package com.example.ActivityService.config;

import org.springframework.amqp.core.*;
import org.springframework.amqp.rabbit.connection.ConnectionFactory;
import org.springframework.amqp.rabbit.core.RabbitTemplate;
import org.springframework.amqp.support.converter.Jackson2JsonMessageConverter;
import org.springframework.amqp.support.converter.MessageConverter;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;

@Configuration
public class RabbitConfig {
    
    @Value("${rabbitmq.exchange.name}")
    private String exchangeName;
    
    @Value("${rabbitmq.routing.key}")
    private String routingKey;
    
    @Bean
    public Queue activityQueue() {
        return new Queue("activity.queue", true);
    }
    
    @Bean
    public TopicExchange activityExchange() {
        return new TopicExchange(exchangeName);
    }
    
    @Bean
    public Binding activityBinding(Queue activityQueue, TopicExchange activityExchange) {
        return BindingBuilder.bind(activityQueue).to(activityExchange).with(routingKey);
    }
    
    @Bean
    public MessageConverter jsonMessageConverter() {
        return new Jackson2JsonMessageConverter();
    }
    
    @Bean
    public RabbitTemplate rabbitTemplate(ConnectionFactory connectionFactory) {
        RabbitTemplate template = new RabbitTemplate(connectionFactory);
        template.setMessageConverter(jsonMessageConverter());
        return template;
    }

    @Bean
    public  DirectExchange directExchange() {
        return new DirectExchange("fitnerse.exchange");
    }

    @Bean
    public  Binding activityBinding(Queue actiyvityQuery, DirectExchange directExchange) {
        return BindingBuilder.bind(actiyvityQuery).to(directExchange).with("activity.tracking");

    }

}