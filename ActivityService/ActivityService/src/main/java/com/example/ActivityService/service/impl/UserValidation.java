package com.example.ActivityService.service.impl;

import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;
import org.springframework.web.reactive.function.client.WebClient;
import org.springframework.web.reactive.function.client.WebClientException;
import org.springframework.web.reactive.function.client.WebClientResponseException;
import reactor.core.publisher.Mono;

import java.time.Duration;

@Service
@RequiredArgsConstructor
public class UserValidation {
    private final WebClient userServiceWebClient;

    public Boolean validateUser(String userId){
        try {
            System.out.println("Attempting to validate user with ID: " + userId);
            Boolean isValid = userServiceWebClient.get()
                    .uri("/api/users/{userId}/validate", userId)
                    .retrieve()
                    .bodyToMono(Boolean.class)
                    .timeout(Duration.ofSeconds(5)) // Add timeout
                    .block();
            System.out.println("User validation response for ID " + userId + ": " + isValid);
            return isValid != null && isValid;
        } catch (WebClientResponseException e) {
            System.err.println("WebClientResponseException while validating user: " + e.getMessage());
            System.err.println("Status code: " + e.getStatusCode());
            System.err.println("Response body: " + e.getResponseBodyAsString());
            if (e.getStatusCode().value() == 404) {
                return false;
            }
            throw new RuntimeException("Error validating user: " + e.getMessage(), e);
        } catch (WebClientException e) {
            System.err.println("WebClientException while validating user: " + e.getMessage());
            if (e.getMessage().contains("404")) {
                return false;
            }
            throw new RuntimeException("Error validating user: " + e.getMessage(), e);
        } catch (Exception e) {
            System.err.println("Unexpected exception while validating user: " + e.getMessage());
            e.printStackTrace();
            throw new RuntimeException("Unexpected error validating user: " + e.getMessage(), e);
        }
    }
}