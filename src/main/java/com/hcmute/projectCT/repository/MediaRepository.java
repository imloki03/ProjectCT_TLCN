package com.hcmute.projectCT.repository;

import com.hcmute.projectCT.model.Media;
import org.springframework.data.jpa.repository.JpaRepository;

import java.util.UUID;

public interface MediaRepository extends JpaRepository<Media, UUID> {
}