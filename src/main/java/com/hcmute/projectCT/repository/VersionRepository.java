package com.hcmute.projectCT.repository;

import com.hcmute.projectCT.model.Project;
import com.hcmute.projectCT.model.Version;
import org.springframework.data.jpa.repository.JpaRepository;

import java.util.List;

public interface VersionRepository extends JpaRepository<Version, Long> {
    List<Version> findByProject(Project project);
}