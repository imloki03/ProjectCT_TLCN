package com.hcmute.projectCT.repository;

import com.hcmute.projectCT.model.Project;
import org.springframework.data.jpa.repository.JpaRepository;

import java.util.List;
import java.util.Optional;

public interface ProjectRepository extends JpaRepository<Project, Long> {
    List<Project> findByOwner_Username(String username);

    List<Project> findByOwner_UsernameOrCollaboratorList_User_Username(String username, String username1);
}