package com.hcmute.projectCT.repository;

import com.hcmute.projectCT.model.Message;
import com.hcmute.projectCT.model.Project;
import org.springframework.data.jpa.repository.JpaRepository;

import java.util.List;

public interface MessageRepository extends JpaRepository<Message, Long> {
    List<Message> findByProject(Project project);

    List<Message> findByContentContaining(String keyword);
}