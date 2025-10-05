package com.fitnersse.aiService.model;

import lombok.*;
import org.springframework.data.annotation.CreatedDate;
import org.springframework.data.annotation.Id;
import org.springframework.data.mongodb.core.mapping.Document;

import java.time.LocalDateTime;
import java.util.List;
@Document(collection = "recomendations")
@Data
@AllArgsConstructor
@NoArgsConstructor
@Getter
@Setter
@Builder
public class Recomendation {
    @Id
    private String id;
    private String activityId;
    private String userID;
    private String activityType;
    private String recomendation;
    private List<String> improments;
    private List<String>  suggestions;
    private List<String>  safety;
    @CreatedDate
    private LocalDateTime createdAt;

}
