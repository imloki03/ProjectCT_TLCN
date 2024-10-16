package com.hcmute.projectCT.repository;

import com.hcmute.projectCT.model.Task;
import org.springframework.data.jpa.repository.JpaRepository;

public interface TaskRepository extends JpaRepository<Task, Long> {
}