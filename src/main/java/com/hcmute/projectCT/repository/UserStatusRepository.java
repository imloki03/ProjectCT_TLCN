package com.hcmute.projectCT.repository;

import com.hcmute.projectCT.model.UserStatus;
import org.springframework.data.jpa.repository.JpaRepository;

public interface UserStatusRepository extends JpaRepository<UserStatus, Long> {
}