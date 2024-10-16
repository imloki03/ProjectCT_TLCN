package com.hcmute.projectCT.repository;

import com.hcmute.projectCT.model.Message;
import org.springframework.data.jpa.repository.JpaRepository;

public interface MessageRepository extends JpaRepository<Message, Long> {
}