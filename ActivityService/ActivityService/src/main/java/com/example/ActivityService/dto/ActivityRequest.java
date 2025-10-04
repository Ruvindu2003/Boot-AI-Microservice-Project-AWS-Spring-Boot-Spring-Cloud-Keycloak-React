package com.example.ActivityService.dto;

import com.example.ActivityService.util.ActivityType;
import jakarta.validation.constraints.NotBlank;
import jakarta.validation.constraints.NotNull;
import lombok.*;


import java.time.LocalDateTime;
import java.util.Map;

@Data
@Getter
@Setter
@AllArgsConstructor
@NoArgsConstructor
public class ActivityRequest {

    @NotBlank(message = "User ID is required")
    private String userId;
    
    @NotNull(message = "Activity type is required")
    private ActivityType activityType;
    
    @NotNull(message = "Duration is required")
    private Integer duration;
    
    @NotNull(message = "Calories burned is required")
    private Integer caloriesBurned;
    
    @NotNull(message = "Start time is required")
    private LocalDateTime startTime;
    
    private LocalDateTime createdAt;
    private Map<String, Object> additionalMetrics;

}