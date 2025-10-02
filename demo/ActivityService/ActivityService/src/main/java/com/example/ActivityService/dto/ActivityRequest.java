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

    private String ueerId;
    private ActivityType activityType;
    private Integer duratuion;
    private Integer caloriseBurend;
    private LocalDateTime startTime;
    private LocalDateTime CreatedAt;
    private Map<String, Object> additonalMetics;

}