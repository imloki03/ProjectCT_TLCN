package com.hcmute.projectCT.repository;

import com.hcmute.projectCT.model.Project;
import org.springframework.data.jpa.repository.JpaRepository;

import java.util.UUID;

public interface ProjectRepository extends JpaRepository<Project, UUID> {
}