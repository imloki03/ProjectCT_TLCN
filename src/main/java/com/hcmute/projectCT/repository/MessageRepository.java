package com.hcmute.projectCT.repository;

import com.hcmute.projectCT.model.Message;
import org.springframework.data.jpa.repository.JpaRepository;

import java.util.UUID;

public interface MessageRepository extends JpaRepository<Message, UUID> {
}