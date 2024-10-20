package com.hcmute.projectCT.repository;

import com.hcmute.projectCT.model.Media;
import com.hcmute.projectCT.model.MediaContent;
import org.springframework.data.jpa.repository.JpaRepository;

import java.util.List;

public interface MediaContentRepository extends JpaRepository<MediaContent, Long> {
    List<MediaContent> findByMedia(Media media);
}