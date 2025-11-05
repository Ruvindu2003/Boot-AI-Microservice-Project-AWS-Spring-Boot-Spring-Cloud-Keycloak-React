package com.fitnersse.aiService.model;

import java.time.Instant;

public class Recommendation {
    private String id;
    private String userId;
    private String activityId;
    private String suggestedActivity;
    private String notes;
    private Double score;
    private String timestamp;

    public Recommendation() {
        this.timestamp = Instant.now().toString();
    }

    public Recommendation(String id, String userId, String activityId, String suggestedActivity, String notes, Double score) {
        this.id = id;
        this.userId = userId;
        this.activityId = activityId;
        this.suggestedActivity = suggestedActivity;
        this.notes = notes;
        this.score = score;
        this.timestamp = Instant.now().toString();
    }

    public String getId() { return id; }
    public void setId(String id) { this.id = id; }

    public String getUserId() { return userId; }
    public void setUserId(String userId) { this.userId = userId; }

    public String getActivityId() { return activityId; }
    public void setActivityId(String activityId) { this.activityId = activityId; }

    public String getSuggestedActivity() { return suggestedActivity; }
    public void setSuggestedActivity(String suggestedActivity) { this.suggestedActivity = suggestedActivity; }

    public String getNotes() { return notes; }
    public void setNotes(String notes) { this.notes = notes; }

    public Double getScore() { return score; }
    public void setScore(Double score) { this.score = score; }

    public String getTimestamp() { return timestamp; }
    public void setTimestamp(String timestamp) { this.timestamp = timestamp; }
}

