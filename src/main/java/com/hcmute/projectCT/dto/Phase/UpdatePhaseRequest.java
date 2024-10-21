package com.hcmute.projectCT.dto.Phase;

import com.hcmute.projectCT.enums.Status;
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
public class UpdatePhaseRequest {
    String name;
    String description;
    LocalDate startDate;
    LocalDate endDate;
}
