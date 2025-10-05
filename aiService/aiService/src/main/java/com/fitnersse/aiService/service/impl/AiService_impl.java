package com.fitnersse.aiService.service.impl;

import com.fitnersse.aiService.model.Recomendation;
import com.fitnersse.aiService.repository.AiRepositoory;
import com.fitnersse.aiService.service.AiService;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;

import java.util.List;


@Service
@RequiredArgsConstructor
public class AiService_impl implements AiService {

    private final AiRepositoory aiRepositoory;


    @Override
    public Recomendation getAllActivityId(String activityId) {

        Recomendation activityNotFound = aiRepositoory.findAllByActivityId(activityId)
                .stream()
                .findFirst()
                .orElseThrow(() -> new RuntimeException("Activity not found"));

        return activityNotFound;
    }

    @Override
    public List<Recomendation> getAllUserRecomendation(String userId) {
      return   aiRepositoory.findAllByUserId(userId);
    }
}
