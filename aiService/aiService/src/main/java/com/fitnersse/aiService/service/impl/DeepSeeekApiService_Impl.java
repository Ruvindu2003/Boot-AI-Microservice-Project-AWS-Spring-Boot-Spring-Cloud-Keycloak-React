package com.fitnersse.aiService.service.impl;

import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.http.HttpHeaders;
import org.springframework.http.MediaType;
import org.springframework.stereotype.Service;
import org.springframework.web.reactive.function.client.WebClient;
import org.springframework.web.reactive.function.client.WebClientResponseException;

import java.util.List;
import java.util.Map;

@Service
@Slf4j
@RequiredArgsConstructor
public class DeepSeeekApiService_Impl {


    private WebClient webClient;
    @Value("${deepSeek.api.url}")
    private String DEEPSEEK_API_URL;
    @Value("${deepSeek.api.key}")
    private String DEEPSEEK_API_KEY;

    public DeepSeeekApiService_Impl(WebClient.Builder webClientBuilder) {
        this.webClient = webClientBuilder.build();

    }

    public String getAnswer(String question) {
        try {
            Map<String, Object> requestBody = Map.of(
                    "model", "deepseek-chat",
                    "messages", List.of(
                            Map.of("role", "user", "content", question)
                    ),
                    "stream", false
            );
            
            String response = webClient.post()
                    .uri(DEEPSEEK_API_URL)
                    .header(HttpHeaders.AUTHORIZATION, "Bearer " + DEEPSEEK_API_KEY)
                    .header(HttpHeaders.CONTENT_TYPE, MediaType.APPLICATION_JSON_VALUE)
                    .bodyValue(requestBody)
                    .retrieve()
                    .bodyToMono(String.class)
                    .block();
                    
            log.info("DeepSeek API Response: {}", response);
            return response;
        } catch (WebClientResponseException e) {
            log.error("Error calling DeepSeek API. Status: {}, Response: {}", e.getStatusCode(), e.getResponseBodyAsString());
            throw new RuntimeException("Failed to get response from DeepSeek API", e);
        } catch (Exception e) {
            log.error("Unexpected error calling DeepSeek API: ", e);
            throw new RuntimeException("Unexpected error when calling DeepSeek API", e);
        }
    }
}