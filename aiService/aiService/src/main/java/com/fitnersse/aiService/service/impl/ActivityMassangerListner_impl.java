package com.fitnersse.aiService.service.impl;

import com.fitnersse.aiService.model.Activity;
import com.fitnersse.aiService.service.ActivityMassangerListner;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.amqp.rabbit.annotation.RabbitListener;
import org.springframework.stereotype.Service;

@RequiredArgsConstructor
@Service
@Slf4j
public class ActivityMassangerListner_impl implements ActivityMassangerListner {
    private final ActivityAiService activityAiService;

    @RabbitListener(queues = "activity.queue")
    @Override
    public void processesActivity(Activity activity) {
        try {
            log.info("ActivityMassangerListner_impl::processesActivity {}", activity.getId());
            String result = activityAiService.generateActivityDescription(activity);
            log.info("ActivityMassangerListner_impl::processesActivity Name {}", result);
        } catch (Exception e) {
            log.error("ActivityMassangerListner_impl::processesActivity encountered an error for activity {}: ", 
                     activity != null ? activity.getId() : "null", e);
        }
    }
}