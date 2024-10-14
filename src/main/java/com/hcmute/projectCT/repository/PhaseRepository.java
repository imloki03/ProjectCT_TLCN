package com.hcmute.projectCT.repository;

import com.hcmute.projectCT.model.Phase;
import org.springframework.data.jpa.repository.JpaRepository;

import java.util.UUID;

public interface PhaseRepository extends JpaRepository<Phase, UUID> {
}