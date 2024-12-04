package com.hcmute.projectCT.configuration;

import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.context.annotation.Configuration;
import org.springframework.web.reactive.function.client.WebClient;

@Configuration
@Slf4j
@RequiredArgsConstructor
public class GeminiConfig {
    @Value("${gemini.api.key}")
    private String apiKey;

    @Value("${gemini.api.url}")
    private String apiUrl;

    public WebClient webClient() {
        return WebClient.builder()
                .baseUrl(apiUrl+apiKey)
                .defaultHeader("Content-Type", "application/json")
                .build();
    }
}
