package com.example.ActivityService.model;

import com.example.ActivityService.util.ActivityType;
import lombok.*;
import org.springframework.data.annotation.CreatedDate;
import org.springframework.data.annotation.Id;
import org.springframework.data.annotation.LastModifiedDate;
import org.springframework.data.mongodb.core.mapping.Document;
import org.springframework.data.mongodb.core.mapping.Field;

import java.time.LocalDateTime;
import java.util.Map;

@Document(collation = "activity")
@Data
@Builder
@AllArgsConstructor
@NoArgsConstructor
@Getter
@Setter
public class Activity {
    @Id


    private  Long id;
    private String userId;
    private ActivityType activityType;
    private Integer duratuion;
    private Integer  caloriseBurend;
    private LocalDateTime startTime;
    @CreatedDate
    private LocalDateTime CreatedAt;
    @Field("metrics")
    private Map<String,Object>additonalMetics;
    @LastModifiedDate
    private LocalDateTime updateAt;


}
