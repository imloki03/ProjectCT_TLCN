package com.hcmute.projectCT.repository;

import com.hcmute.projectCT.model.Collaborator;
import org.springframework.data.jpa.repository.JpaRepository;

public interface CollaboratorRepository extends JpaRepository<Collaborator, Long> {
}