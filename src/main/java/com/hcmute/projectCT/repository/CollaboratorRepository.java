package com.hcmute.projectCT.repository;

import com.hcmute.projectCT.model.Collaborator;
import org.springframework.data.jpa.repository.JpaRepository;

import java.util.UUID;

public interface CollaboratorRepository extends JpaRepository<Collaborator, UUID> {
}