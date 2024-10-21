package com.hcmute.projectCT.repository;

import com.hcmute.projectCT.model.Collaborator;
import org.springframework.data.jpa.repository.JpaRepository;

import java.util.List;

public interface CollaboratorRepository extends JpaRepository<Collaborator, Long> {
    List<Collaborator> findByProject_Id(Long id);
}