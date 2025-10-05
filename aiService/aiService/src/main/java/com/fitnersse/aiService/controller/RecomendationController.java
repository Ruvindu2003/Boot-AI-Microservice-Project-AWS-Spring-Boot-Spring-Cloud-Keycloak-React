package com.fitnersse.aiService.controller;


import com.fitnersse.aiService.model.Recomendation;
import com.fitnersse.aiService.service.AiService;
import lombok.RequiredArgsConstructor;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.util.List;

@RestController
@RequestMapping("/api/recomendations")
@CrossOrigin
@RequiredArgsConstructor

public class RecomendationController {

    private final AiService aiService;

    @GetMapping("/user/{userId}")
    public ResponseEntity<List<Recomendation>>getUserRecomendations(@PathVariable String userId){
        return ResponseEntity.ok(aiService.getAllUserRecomendation(userId));
    }

    @GetMapping("/activity/{activityId}")
    public ResponseEntity<Recomendation>getActivityRecomendation(@PathVariable String activityId){
        return ResponseEntity.ok(aiService.getAllActivityId(activityId));
    }


}
