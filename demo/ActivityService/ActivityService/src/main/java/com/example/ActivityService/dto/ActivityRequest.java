package com.example.ActivityService.dto;

import com.example.ActivityService.util.ActivityType;
import lombok.*;

import java.time.LocalDateTime;
import java.util.Map;
@Data
@Getter
@Setter
@AllArgsConstructor
@NoArgsConstructor
public class ActivityRequest {

    private String userId;
    private ActivityType activityType;
    private Integer duration;
    private Integer caloriesBurned;
    private LocalDateTime startTime;
    private LocalDateTime createdAt;
    private Map<String, Object> additionalMetrics;

}