package com.example.ActivityService.service.impl;

import com.example.ActivityService.dto.ActivityRequest;
import com.example.ActivityService.dto.ActivityResponce;
import com.example.ActivityService.model.Activity;
import com.example.ActivityService.repository.ActivityRepository;
import com.example.ActivityService.service.ActivityService;

import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.amqp.AmqpConnectException;
import org.springframework.amqp.rabbit.core.RabbitTemplate;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.stereotype.Service;

import java.util.List;

@Slf4j
@Service
@RequiredArgsConstructor
public class ActivityImpl implements ActivityService {
    private final ActivityRepository activityRepository;
    private final UserValidation userValidation;
    private final RabbitTemplate rabbitTemplate;

    @Value("${rabbitmq.exchange.name}")
    private String exChange;
    @Value("${rabbitmq.routing.key}")
    private String routingKey;

    @Override
    public ActivityResponce trackActivity(ActivityRequest activityRequest) {
        log.info("Received activity request: {}", activityRequest);
        log.info("Validating user: {}", activityRequest.getUserId());
        boolean isValidUser = userValidation.validateUser(activityRequest.getUserId());
        log.info("User validation response for ID {}: {}", activityRequest.getUserId(), isValidUser);
        
        if (!isValidUser) {
            throw new RuntimeException("Invalid User ID: " + activityRequest.getUserId());
        }
        
        Activity activity = Activity.builder().
                userId(activityRequest.getUserId()).
                activityType(activityRequest.getActivityType()).
                duration(activityRequest.getDuration()).
                caloriesBurned(activityRequest.getCaloriesBurned()).
                startTime(activityRequest.getStartTime()).
                createdAt(activityRequest.getCreatedAt()).
                additionalMetrics(activityRequest.getAdditionalMetrics()).build();

        Activity saveActivity = activityRepository.save(activity);
        log.info("Activity tracked successfully: {}", mapToActivityResponce(saveActivity));

        try {
            rabbitTemplate.convertAndSend(exChange, routingKey, saveActivity);
            log.info("Activity published to RabbitMQ successfully");
        } catch (AmqpConnectException e) {
            log.error("Failed to connect to RabbitMQ. Activity saved to database but not published to queue.", e);
            // Activity is still saved to database, but not published to queue
            // In a production environment, you might want to implement a retry mechanism or dead letter queue
        } catch (Exception e) {
            log.error("Unexpected error while publishing activity to RabbitMQ", e);
        }

        return mapToActivityResponce(saveActivity);
    }

    @Override
    public List<ActivityResponce> getAllActivity(String userID) {
        List<Activity> activities = activityRepository.findByUserId(userID);
        return activities.stream().map(this::mapToActivityResponce).toList();
    }

    @Override
    public ActivityResponce getActivityById(String activityId) {
        Activity activity = activityRepository.findById(activityId).
                orElseThrow(() -> new RuntimeException("Activity Not Found"));
        return mapToActivityResponce(activity);
    }

    private ActivityResponce mapToActivityResponce(Activity saveActivity) {
        ActivityResponce activityResponce = new ActivityResponce();
        activityResponce.setId(saveActivity.getId());
        activityResponce.setUserId(saveActivity.getUserId());
        activityResponce.setActivityType(saveActivity.getActivityType());
        activityResponce.setDuration(saveActivity.getDuration());
        activityResponce.setCaloriesBurned(saveActivity.getCaloriesBurned());
        activityResponce.setStartTime(saveActivity.getStartTime());
        activityResponce.setCreatedAt(saveActivity.getCreatedAt());
        activityResponce.setAdditionalMetrics(saveActivity.getAdditionalMetrics());
        activityResponce.setUpdatedAt(saveActivity.getUpdatedAt());
        return activityResponce;
    }
}