package com.fitnersse.aiService.service.impl;

import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;
import org.springframework.web.reactive.function.client.WebClient;

import java.util.HashMap;
import java.util.Map;

@Service
@RequiredArgsConstructor
public class GeminiAiServiceImpl {

    private WebClient webClient;

    public  GeminiAiServiceImpl geminiAiService(WebClient.Builder webClientBuilder) {
        this.webClient = webClientBuilder.build();
        return this;
    }

    public String getResponseFromGemini(String prompt) {
        Map<String,Object> requestBody = Map.of(
                "content", new Object[] {
                        Map.of(
                                "type", "text",
                                "text", prompt
                        )
                },
        )
    }
}