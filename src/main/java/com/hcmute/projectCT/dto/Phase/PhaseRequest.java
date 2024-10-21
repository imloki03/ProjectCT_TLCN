package com.hcmute.projectCT.dto.Phase;

import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;

import java.time.LocalDate;

@Data
@Builder
@NoArgsConstructor
@AllArgsConstructor
public class PhaseRequest {
    String name;
    String description;
    LocalDate startDate;
    LocalDate endDate;
}
