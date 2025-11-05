package com.fitnersse.aiService.service.impl;

import com.fitnersse.aiService.model.ActivityDto;
import com.fitnersse.aiService.model.Recommendation;
import com.fitnersse.aiService.service.AiService;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.stereotype.Service;
import org.springframework.web.client.RestTemplate;

import java.util.ArrayList;
import java.util.List;
import java.util.UUID;

@Service
public class AiServiceImpl implements AiService {

    private final RestTemplate restTemplate;
    private final String activityEndpoint;
    private final String deepSeekApiUrl;
    private final String deepSeekApiKey;

    public AiServiceImpl(RestTemplate restTemplate,
                         @Value("${activity.service.url:http://localhost:8082/api/activities}") String activityEndpoint,
                         @Value("${deepSeek.api.url:}") String deepSeekApiUrl,
                         @Value("${deepSeek.api.key:}") String deepSeekApiKey) {
        this.restTemplate = restTemplate;
        this.activityEndpoint = activityEndpoint;
        this.deepSeekApiUrl = deepSeekApiUrl;
        this.deepSeekApiKey = deepSeekApiKey;
    }

    @Override
    public Recommendation getRecommendationByActivityId(String activityId) {
        // Return a mock recommendation for now
        return new Recommendation(UUID.randomUUID().toString(), "user-123", activityId, "Run 5km", "Suggested based on pace", 0.85);
    }

    @Override
    public List<Recommendation> getAllUserRecommendations(String userId) {
        List<Recommendation> list = new ArrayList<>();
        list.add(new Recommendation(UUID.randomUUID().toString(), userId, "act-1", "Run 5km", "Great for stamina", 0.9));
        list.add(new Recommendation(UUID.randomUUID().toString(), userId, "act-2", "Cycle 10km", "Low impact cardio", 0.75));
        return list;
    }

    @Override
    public void postActivityFromRecommendation(Recommendation recommendation) {
        ActivityDto activity = new ActivityDto();
        activity.setUserId(recommendation.getUserId());
        activity.setActivityType(recommendation.getSuggestedActivity());
        activity.setNotes(recommendation.getNotes());
        activity.setTimestamp(recommendation.getTimestamp());

        try {
            restTemplate.postForEntity(activityEndpoint, activity, Void.class);
        } catch (Exception ex) {
            // For now swallow exception, could log or retry
            System.err.println("Failed to post activity: " + ex.getMessage());
        }
    }
}

