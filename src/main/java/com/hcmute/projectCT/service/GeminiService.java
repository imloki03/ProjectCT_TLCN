package com.hcmute.projectCT.service;

import com.fasterxml.jackson.core.JsonProcessingException;

public interface GeminiService {
    String sendMessage(String userMessage) throws JsonProcessingException;
}
