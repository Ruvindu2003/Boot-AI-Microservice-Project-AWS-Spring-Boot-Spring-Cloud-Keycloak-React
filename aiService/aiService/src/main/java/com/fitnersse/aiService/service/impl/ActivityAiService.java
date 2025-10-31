package com.fitnersse.aiService.service.impl;

import com.fasterxml.jackson.databind.ObjectMapper;
import com.fitnersse.aiService.model.Activity;
import com.fitnersse.aiService.model.Recomendation;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.stereotype.Service;

@Service
@Slf4j
@RequiredArgsConstructor
public class ActivityAiService {
    private final DeepSeeekApiService_Impl deepSeeekApiService_impl;


    public String generateActivityDescription(Activity activity) {
    String prompt=createForActivityPrompt(activity);
    String aiResponse= deepSeeekApiService_impl.getAnswer(prompt);
    log.info("ActivityAiService::generateActivityDescription aiResponse {}" ,aiResponse);
    createRecomendationFromAiRespnse(activity,aiResponse);
    return aiResponse;
    }

    private String createForActivityPrompt(Activity activity) {

        return String.format("Generate a detailed and engaging description for the following activity:\n\n" +
                        "Activity Name: %s\n" +
                        "Category: %s\n" +
                        "Duration: %d minutes\n" +
                        "Intensity Level: %s\n" +
                        "Equipment Needed: %s\n\n" +
                        "The description should highlight the benefits of the activity, what participants can expect, and any unique features that make it stand out. Make it appealing and informative.",
                activity.getAdditionalMetrics().get("name"),
                activity.getType(),
                activity.getStartTime(),
                activity.getAdditionalMetrics().get("intensity_level"),
                activity.getDuration(),
                activity.getAdditionalMetrics().get("equipment")
        );
    }


    public void createRecomendationFromAiRespnse(Activity activity,String aiResponse){
        try {


            ObjectMapper objectMapper=new ObjectMapper();


        }catch (Exception e){
            e.printStackTrace();

        }
    }


}
