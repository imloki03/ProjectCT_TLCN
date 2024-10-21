package com.hcmute.projectCT.dto.Phase;

import com.hcmute.projectCT.enums.Status;
import com.hcmute.projectCT.model.Phase;
import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;

import java.time.LocalDate;
import java.time.LocalDateTime;

@Data
@Builder
@NoArgsConstructor
@AllArgsConstructor
public class PhaseResponse {
    Long id;
    String name;
    String description;
    LocalDate startDate;
    LocalDate endDate;
    Status status;
    LocalDateTime createdDate;

    public PhaseResponse(Phase phase){
        this.id = phase.getId();
        this.name = phase.getName();
        this.description = phase.getDescription();
        this.startDate = phase.getStartDate();
        this.endDate = phase.getEndDate();
        this.status = phase.getStatus();
        this.createdDate = phase.getCreatedDate();
    }
}
