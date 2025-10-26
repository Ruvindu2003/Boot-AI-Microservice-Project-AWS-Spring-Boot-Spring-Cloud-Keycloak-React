package com.fitnersse.aiService.service.impl;

import lombok.RequiredArgsConstructor;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.stereotype.Service;
import org.springframework.web.reactive.function.client.WebClient;

import java.util.Map;

@Service
@RequiredArgsConstructor
public class DeepSeeekApiService_Impl {


    private WebClient webClient;
    @Value("${deepseek.api.url}")
    private String DEEPSEEK_API_URL;
    @Value("${deepseek.api.key}")
    private String DEEPSEEK_API_KEY;

    public DeepSeeekApiService_Impl(WebClient.Builder webClientBuilder) {
        this.webClient = webClientBuilder.build();

    }

    public String getAnswer(String question) {
        Map<String, Object> requestBody = Map.of(
                "model", "gemini-1.5-pro",
                "messages", new Object[]{
                        Map.of("text", question)
                }
        );
     String respone= webClient.post()
             .uri(DEEPSEEK_API_URL+DEEPSEEK_API_KEY)
             .header( "Content-Type", "application/json" )
             .bodyValue(requestBody)
             .retrieve()
             .bodyToMono(String.class)
             .block();
     return respone;
    }



}