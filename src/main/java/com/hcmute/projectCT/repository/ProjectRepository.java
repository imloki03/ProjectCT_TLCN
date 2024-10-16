package com.hcmute.projectCT.repository;

import com.hcmute.projectCT.model.Project;
import org.springframework.data.jpa.repository.JpaRepository;

public interface ProjectRepository extends JpaRepository<Project, Long> {
}