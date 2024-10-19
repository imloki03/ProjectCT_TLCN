package com.hcmute.projectCT.repository;

import com.hcmute.projectCT.model.Task;
import org.springframework.data.jpa.repository.JpaRepository;

import java.util.List;

public interface TaskRepository extends JpaRepository<Task, Long> {

    List<Task> findByBacklog_Project_Id(Long id);
}