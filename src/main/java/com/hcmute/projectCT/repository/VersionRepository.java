package com.hcmute.projectCT.repository;

import com.hcmute.projectCT.model.Version;
import org.springframework.data.jpa.repository.JpaRepository;

public interface VersionRepository extends JpaRepository<Version, Long> {
}