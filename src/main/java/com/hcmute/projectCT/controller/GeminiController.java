package com.hcmute.projectCT.controller;

import com.fasterxml.jackson.core.JsonProcessingException;
import com.hcmute.projectCT.constant.MessageKey;
import com.hcmute.projectCT.dto.Gemini.TextPart;
import com.hcmute.projectCT.dto.RespondData;
import com.hcmute.projectCT.service.GeminiService;
import com.hcmute.projectCT.util.MessageUtil;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

@Slf4j
@RestController
@RequestMapping("/api/v1/gemini")
@RequiredArgsConstructor
public class GeminiController {
    final GeminiService openAiService;
    final MessageUtil messageUtil;

    @GetMapping("")
    public ResponseEntity<?> getTask(@RequestBody TextPart message) throws JsonProcessingException {
        String messageRes = openAiService.sendMessage(message.getText());
        var respondData = RespondData
                .builder()
                .status(HttpStatus.OK.value())
                .data(messageRes)
                .desc(messageUtil.getMessage(MessageKey.REQUEST_SUCCESS))
                .build();
        return new ResponseEntity<>(respondData, HttpStatus.OK);
    }
}
