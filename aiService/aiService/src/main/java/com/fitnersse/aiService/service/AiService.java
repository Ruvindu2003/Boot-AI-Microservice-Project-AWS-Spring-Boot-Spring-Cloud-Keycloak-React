package com.fitnersse.aiService.service;

import com.fitnersse.aiService.model.Recommendation;

import java.util.List;

public interface AiService {

    Recommendation getRecommendationByActivityId(String activityId);

    List<Recommendation> getAllUserRecommendations(String userId);

    void postActivityFromRecommendation(Recommendation recommendation);
}
