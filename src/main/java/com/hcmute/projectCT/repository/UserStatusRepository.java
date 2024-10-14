package com.hcmute.projectCT.repository;

import com.hcmute.projectCT.model.UserStatus;
import org.springframework.data.jpa.repository.JpaRepository;

import java.util.UUID;

public interface UserStatusRepository extends JpaRepository<UserStatus, UUID> {
}