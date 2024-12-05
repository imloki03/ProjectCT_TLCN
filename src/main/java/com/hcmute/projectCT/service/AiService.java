package com.hcmute.projectCT.service;

import com.fasterxml.jackson.core.JsonProcessingException;
import com.hcmute.projectCT.dto.AI.AssignTaskResponse;

public interface AiService {
    AssignTaskResponse recommendTaskAssignee(Long id) throws JsonProcessingException;
}
