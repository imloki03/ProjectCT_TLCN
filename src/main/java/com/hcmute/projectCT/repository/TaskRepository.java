package com.hcmute.projectCT.repository;

import com.hcmute.projectCT.model.Task;
import org.springframework.data.jpa.repository.JpaRepository;

import java.util.UUID;

public interface TaskRepository extends JpaRepository<Task, UUID> {
}