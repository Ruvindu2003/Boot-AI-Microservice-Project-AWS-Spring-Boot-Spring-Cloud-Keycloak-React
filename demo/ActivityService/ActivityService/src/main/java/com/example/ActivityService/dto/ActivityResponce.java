package com.example.ActivityService.dto;

import com.example.ActivityService.util.ActivityType;
import lombok.*;


import java.time.LocalDateTime;
import java.util.Map;

@Getter
@Setter
@AllArgsConstructor
@NoArgsConstructor
@Data

public class ActivityResponce {

    private  String id;
    private String userId;
    private ActivityType activityType;
    private Integer duratuion;
    private Integer  caloriseBurend;
    private LocalDateTime startTime;
    private LocalDateTime CreatedAt;
    private Map<String,Object> additonalMetics;
    private LocalDateTime updateAt;
}
