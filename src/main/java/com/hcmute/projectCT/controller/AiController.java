package com.hcmute.projectCT.controller;

import com.fasterxml.jackson.core.JsonProcessingException;
import com.hcmute.projectCT.constant.MessageKey;
import com.hcmute.projectCT.dto.AI.AssignTaskResponse;
import com.hcmute.projectCT.dto.Collaborator.CollaboratorResponse;
import com.hcmute.projectCT.dto.RespondData;
import com.hcmute.projectCT.service.AiService;
import com.hcmute.projectCT.util.MessageUtil;
import io.swagger.v3.oas.annotations.tags.Tag;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.util.List;

@Slf4j
@RestController
@RequestMapping("api/v1/ai")
@RequiredArgsConstructor
@Tag(name = "AI Controller")
public class AiController {
    final AiService aiService;
    final MessageUtil messageUtil;

    @PostMapping("task/{taskId}")
    public ResponseEntity<?> recommendTaskAssignee(@PathVariable Long taskId) throws JsonProcessingException {
        AssignTaskResponse response = aiService.recommendTaskAssignee(taskId);
        var respondData = RespondData
                .builder()
                .status(HttpStatus.OK.value())
                .data(response)
                .desc(messageUtil.getMessage(MessageKey.REQUEST_SUCCESS))
                .build();
        return new ResponseEntity<>(respondData, HttpStatus.OK);
    }
}
