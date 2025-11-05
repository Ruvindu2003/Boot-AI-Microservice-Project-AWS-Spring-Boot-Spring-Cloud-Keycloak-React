package com.example.demo.client;

import com.example.demo.model.ActivityDto;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.stereotype.Component;
import org.springframework.web.client.RestTemplate;

import java.util.Arrays;
import java.util.Collections;
import java.util.List;

@Component
public class ActivityClient {

    private final RestTemplate restTemplate;
    private final String activityBaseUrl;

    public ActivityClient(RestTemplate restTemplate, @Value("${activity.service.url:http://localhost:8082/api/activities}") String activityBaseUrl) {
        this.restTemplate = restTemplate;
        this.activityBaseUrl = activityBaseUrl;
    }

    public void postActivity(ActivityDto activity) {
        restTemplate.postForEntity(activityBaseUrl, activity, Void.class);
    }

    public List<ActivityDto> searchActivities(Long userId, String q) {
        try {
            String url = activityBaseUrl + "/search?userId=" + userId;
            if (q != null && !q.isEmpty()) url += "&q=" + q;
            ActivityDto[] arr = restTemplate.getForObject(url, ActivityDto[].class);
            if (arr == null) return Collections.emptyList();
            return Arrays.asList(arr);
        } catch (Exception ex) {
            // If activity service is unavailable or endpoint missing, return empty list.
            return Collections.emptyList();
        }
    }
}

