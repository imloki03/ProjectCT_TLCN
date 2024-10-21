package com.hcmute.projectCT.repository;

import com.hcmute.projectCT.model.Phase;
import org.springframework.data.jpa.repository.JpaRepository;

import java.util.List;

public interface PhaseRepository extends JpaRepository<Phase, Long> {
    List<Phase> findByProject_Id(Long id);
}