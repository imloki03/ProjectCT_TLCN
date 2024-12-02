package com.hcmute.projectCT.repository;

import com.hcmute.projectCT.model.Message;
import com.hcmute.projectCT.model.Project;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.data.jpa.repository.JpaRepository;

import java.util.List;

public interface MessageRepository extends JpaRepository<Message, Long> {
    List<Message> findByProject(Project project);
    List<Message> findByProjectAndContentContaining(Project project, String keyword);
    List<Message> findByContentContaining(String keyword);

    Page<Message> findAllByProject(Project project, Pageable pageable);

    List<Message> findByProjectAndIsPinned(Project project, boolean isPinned);
}