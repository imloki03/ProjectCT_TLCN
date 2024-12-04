package com.hcmute.projectCT.service;

import com.fasterxml.jackson.core.JsonProcessingException;
import com.fasterxml.jackson.databind.JsonNode;
import com.fasterxml.jackson.databind.ObjectMapper;
import com.hcmute.projectCT.configuration.GeminiConfig;
import com.hcmute.projectCT.dto.Gemini.Part;
import com.hcmute.projectCT.dto.Gemini.TextPart;
import com.hcmute.projectCT.dto.Gemini.GeminiRequest;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.stereotype.Service;
import org.springframework.web.reactive.function.client.WebClient;

import java.time.Duration;
import java.util.List;

@Service
@Slf4j
@RequiredArgsConstructor
public class GeminiServiceImpl implements GeminiService {
    final GeminiConfig geminiConfig;
    @Override
    public String sendMessage(String message) throws JsonProcessingException {
        WebClient webClient = geminiConfig.webClient();

        TextPart textPart = TextPart
                .builder()
                .text(message)
                .build();

        Part part = Part.builder()
                .parts(List.of(textPart))
                .build();

        GeminiRequest request = GeminiRequest
                .builder()
                .contents(List.of(
                    part
                ))
                .build();

        String response = webClient.post()
                .bodyValue(request)
                .retrieve()
                .bodyToMono(String.class)
                .block();

        ObjectMapper objectMapper = new ObjectMapper();
        JsonNode rootNode = objectMapper.readTree(response);

        return rootNode
                .path("candidates")
                .get(0)
                .path("content")
                .path("parts")
                .get(0)
                .path("text")
                .asText();
    }
}
