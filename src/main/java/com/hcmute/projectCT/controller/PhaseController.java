package com.hcmute.projectCT.controller;

import com.hcmute.projectCT.constant.MessageKey;
import com.hcmute.projectCT.dto.Phase.PhaseRequest;
import com.hcmute.projectCT.dto.Phase.PhaseResponse;
import com.hcmute.projectCT.dto.Phase.UpdatePhaseRequest;
import com.hcmute.projectCT.dto.RespondData;
import com.hcmute.projectCT.enums.Status;
import com.hcmute.projectCT.service.PhaseService;
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
@RequestMapping("api/v1/phase")
@RequiredArgsConstructor
@Tag(name = "Project Controller")
public class PhaseController {
    final PhaseService phaseService;
    final MessageUtil messageUtil;

    @PostMapping("{projectId}")
    public ResponseEntity<?> createNewPhase(@PathVariable Long projectId, @RequestBody PhaseRequest phaseRequest){
        phaseService.createNewPhase(projectId, phaseRequest);
        var respondData = RespondData
                .builder()
                .status(HttpStatus.OK.value())
                .desc(messageUtil.getMessage(MessageKey.PHASE_CREATE_SUCCESS))
                .build();
        return new ResponseEntity<>(respondData, HttpStatus.OK);
    }

    @GetMapping("{projectId}/{phaseId}")
    public ResponseEntity<?> getPhase(@PathVariable Long projectId, @PathVariable Long phaseId) {
        PhaseResponse phaseResponse = phaseService.getPhase(phaseId);
        var respondData = RespondData
                .builder()
                .status(HttpStatus.OK.value())
                .data(phaseResponse)
                .desc(messageUtil.getMessage(MessageKey.REQUEST_SUCCESS))
                .build();
        return new ResponseEntity<>(respondData, HttpStatus.OK);
    }

    @GetMapping("{projectId}")
    public ResponseEntity<?> getAllPhase(@PathVariable Long projectId){
        List<PhaseResponse> phaseResponses = phaseService.getAllPhase(projectId);
        var respondData = RespondData
                .builder()
                .status(HttpStatus.OK.value())
                .data(phaseResponses)
                .desc(messageUtil.getMessage(MessageKey.REQUEST_SUCCESS))
                .build();
        return new ResponseEntity<>(respondData, HttpStatus.OK);
    }

    @PutMapping("{projectId}/{phaseId}")
    public ResponseEntity<?> updatePhase(@PathVariable Long projectId, @PathVariable Long phaseId, @RequestBody UpdatePhaseRequest updatePhaseRequest) {
        phaseService.updatePhase(phaseId, updatePhaseRequest);
        var respondData = RespondData
                .builder()
                .status(HttpStatus.OK.value())
                .desc(messageUtil.getMessage(MessageKey.PHASE_UPDATE_SUCCESS))
                .build();
        return new ResponseEntity<>(respondData, HttpStatus.OK);
    }

    @DeleteMapping("{projectId}/{phaseId}")
    public ResponseEntity<?> deletePhase(@PathVariable Long projectId, @PathVariable Long phaseId) {
        phaseService.deletePhase(phaseId);
        var respondData = RespondData
                .builder()
                .status(HttpStatus.OK.value())
                .desc(messageUtil.getMessage(MessageKey.PHASE_DELETE_SUCCESS))
                .build();
        return new ResponseEntity<>(respondData, HttpStatus.OK);
    }

    @PatchMapping("{projectId}/{phaseId}/assign/{assigneeUsername}")
    public ResponseEntity<?> assignTask(@PathVariable Long projectId, @PathVariable Long phaseId, @PathVariable String assigneeUsername) {
        phaseService.assignTask(phaseId, assigneeUsername);
        var respondData = RespondData
                .builder()
                .status(HttpStatus.OK.value())
                .desc(messageUtil.getMessage(MessageKey.REQUEST_SUCCESS))
                .build();
        return new ResponseEntity<>(respondData, HttpStatus.OK);
    }

    @PatchMapping("{projectId}/{phaseId}/status/{status}")
    public ResponseEntity<?> updateStatus(@PathVariable Long projectId, @PathVariable Long phaseId, @PathVariable Status status) {
        phaseService.updateTaskStatus(phaseId, status);
        var respondData = RespondData
                .builder()
                .status(HttpStatus.OK.value())
                .desc(messageUtil.getMessage(MessageKey.REQUEST_SUCCESS))
                .build();
        return new ResponseEntity<>(respondData, HttpStatus.OK);
    }

    @PatchMapping("{projectId}/{phaseId}/backlog/{taskId}")
    public ResponseEntity<?> moveTaskToBacklog(@PathVariable Long projectId, @PathVariable Long phaseId, @PathVariable Long taskId) {
        phaseService.moveTaskToBacklog(taskId);
        var respondData = RespondData
                .builder()
                .status(HttpStatus.OK.value())
                .desc(messageUtil.getMessage(MessageKey.PHASE_MOVE_SUCCESS))
                .build();
        return new ResponseEntity<>(respondData, HttpStatus.OK);
    }
}
