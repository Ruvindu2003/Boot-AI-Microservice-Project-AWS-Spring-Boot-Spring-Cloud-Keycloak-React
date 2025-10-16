package com.fitnersse.aiService.service.impl;

import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;
import org.springframework.web.reactive.function.client.WebClient;

@Service
@RequiredArgsConstructor
public class DeepSeeekApiService_Impl {

    private WebClient webClient;

    public DeepSeeekApiService_Impl geminiAiService(WebClient.Builder webClientBuilder) {
        this.webClient = webClientBuilder.build();
        return this;
    }


}