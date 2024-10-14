package com.hcmute.projectCT.repository;

import com.hcmute.projectCT.model.Tag;
import org.springframework.data.jpa.repository.JpaRepository;

import java.util.UUID;

public interface TagRepository extends JpaRepository<Tag, UUID> {
}