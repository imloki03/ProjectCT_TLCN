package com.hcmute.projectCT.repository;

import com.hcmute.projectCT.model.Notification;
import org.springframework.data.jpa.repository.JpaRepository;

public interface NotificationRepository extends JpaRepository<Notification, Long> {
}