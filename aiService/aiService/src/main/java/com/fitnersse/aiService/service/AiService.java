package com.fitnersse.aiService.service;

import com.fitnersse.aiService.model.Recomendation;

import java.net.http.HttpResponse;
import java.util.List;

public interface AiService {

   Recomendation getAllActivityId(String activityId);

    List<Recomendation> getAllUserRecomendation(String userId);
}
