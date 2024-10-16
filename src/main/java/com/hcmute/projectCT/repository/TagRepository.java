package com.hcmute.projectCT.repository;

import com.hcmute.projectCT.model.Tag;
import org.springframework.data.jpa.repository.JpaRepository;

public interface TagRepository extends JpaRepository<Tag, Long> {
}