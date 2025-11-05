package com.fitnersse.aiService.service.impl;

import com.fasterxml.jackson.databind.JsonNode;
import com.fasterxml.jackson.databind.ObjectMapper;
import com.fitnersse.aiService.model.Activity;
import com.fitnersse.aiService.model.Recomendation;
import com.fitnersse.aiService.repository.AiRepositoory;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.stereotype.Service;

@Service
@Slf4j
@RequiredArgsConstructor
public class ActivityAiService {
    private final DeepSeeekApiService_Impl deepSeeekApiService_impl;
    private final AiRepositoory aiRepository;


    public String generateActivityDescription(Activity activity) {
        try {
            if (activity == null) {
                log.warn("ActivityAiService::generateActivityDescription received null activity");
                return null;
            }
            
            String prompt = createForActivityPrompt(activity);
            if (prompt == null) {
                log.warn("ActivityAiService::generateActivityDescription generated null prompt");
                return null;
            }
            
            String aiResponse = deepSeeekApiService_impl.getAnswer(prompt);
            log.info("ActivityAiService::generateActivityDescription aiResponse {}", aiResponse);
            
            if (aiResponse != null && !aiResponse.isEmpty()) {
                createRecomendationFromAiRespnse(activity, aiResponse);
            }
            
            return aiResponse;
        } catch (Exception e) {
            log.error("ActivityAiService::generateActivityDescription encountered an error: ", e);
            return null;
        }
    }

    private String createForActivityPrompt(Activity activity) {
        try {
            if (activity.getAdditionalMetrics() == null) {
                log.warn("Activity has null additionalMetrics");
                return null;
            }
            
            String name = (String) activity.getAdditionalMetrics().get("name");
            String intensityLevel = (String) activity.getAdditionalMetrics().get("intensity_level");
            String equipment = (String) activity.getAdditionalMetrics().get("equipment");
            
            if (name == null || activity.getType() == null) {
                log.warn("Activity missing required fields: name={}, type={}", name, activity.getType());
                return null;
            }

            return String.format("Generate a detailed and engaging description for the following activity:\n\n" +
                            "Activity Name: %s\n" +
                            "Category: %s\n" +
                            "Duration: %d minutes\n" +
                            "Intensity Level: %s\n" +
                            "Equipment Needed: %s\n\n" +
                            "The description should highlight the benefits of the activity, what participants can expect, and any unique features that make it stand out. Make it appealing and informative. Respond with a JSON object containing a 'description' field with the activity description.",
                    name,
                    activity.getType(),
                    activity.getDuration() != null ? activity.getDuration() : 0,
                    intensityLevel != null ? intensityLevel : "Not specified",
                    equipment != null ? equipment : "None"
            );
        } catch (Exception e) {
            log.error("ActivityAiService::createForActivityPrompt encountered an error: ", e);
            return null;
        }
    }


    public void createRecomendationFromAiRespnse(Activity activity, String aiResponse) {
        try {
            if (activity == null || aiResponse == null || aiResponse.isEmpty()) {
                log.warn("Invalid input for createRecomendationFromAiRespnse: activity={}, aiResponse={}", 
                         activity != null ? activity.getId() : "null", aiResponse);
                return;
            }

            ObjectMapper objectMapper = new ObjectMapper();
            JsonNode jsonNode = objectMapper.readTree(aiResponse);
            
            String description = null;
            if (jsonNode.has("choices") && jsonNode.get("choices").isArray() && jsonNode.get("choices").size() > 0) {
                JsonNode firstChoice = jsonNode.get("choices").get(0);
                if (firstChoice.has("message") && firstChoice.get("message").has("content")) {
                    description = firstChoice.get("message").get("content").asText();
                }
            } else if (jsonNode.has("message") && jsonNode.get("message").has("content")) {
                description = jsonNode.get("message").get("content").asText();
            } else {
                // Fallback: use the raw response as description
                description = aiResponse;
            }
            
            if (description != null) {
                Recomendation recomendation = Recomendation.builder()
                        .activityId(activity.getId())
                        .userId(activity.getUserId())
                        .activityType(activity.getType())
                        .recomendation(description)
                        .build();
                        
                // Save recommendation to database
                aiRepository.save(recomendation);
                log.info("ActivityAiService::createRecomendationFromAiRespnse saved recommendation with id: {}", recomendation.getId());
            } else {
                log.warn("Could not extract description from AI response: {}", aiResponse);
            }

        } catch (Exception e) {
            log.error("ActivityAiService::createRecomendationFromAiRespnse encountered an error: ", e);
        }
    }
}