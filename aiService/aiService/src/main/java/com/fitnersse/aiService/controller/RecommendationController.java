package com.fitnersse.aiService.controller;

import com.fitnersse.aiService.model.Recommendation;
import com.fitnersse.aiService.service.AiService;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.util.List;

@RestController
@RequestMapping("/recommendations")
public class RecommendationController {

    private final AiService aiService;

    public RecommendationController(AiService aiService) {
        this.aiService = aiService;
    }

    @GetMapping("/activity/{activityId}")
    public ResponseEntity<Recommendation> getByActivityId(@PathVariable String activityId) {
        Recommendation rec = aiService.getRecommendationByActivityId(activityId);
        return ResponseEntity.ok(rec);
    }

    @GetMapping("/user/{userId}")
    public ResponseEntity<List<Recommendation>> getByUserId(@PathVariable String userId) {
        List<Recommendation> list = aiService.getAllUserRecommendations(userId);
        return ResponseEntity.ok(list);
    }

    @PostMapping
    public ResponseEntity<Void> createAndPostActivity(@RequestBody Recommendation recommendation) {
        aiService.postActivityFromRecommendation(recommendation);
        return ResponseEntity.accepted().build();
    }
}

