package com.fitnersse.aiService.repository;

import com.fitnersse.aiService.model.Recomendation;
import org.springframework.data.mongodb.repository.MongoRepository;
import org.springframework.stereotype.Repository;
import org.springframework.web.bind.annotation.RestController;

import java.util.List;

@Repository
public interface AiRepositoory extends MongoRepository<Recomendation, String> {
    List<Recomendation> findAllByUserId(String userId);

    List<Recomendation> findAllByActivityId(String activityId);
}
