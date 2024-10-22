package com.hcmute.projectCT.repository;

import com.hcmute.projectCT.model.Collaborator;
import com.hcmute.projectCT.model.Project;
import com.hcmute.projectCT.model.User;
import org.springframework.data.jpa.repository.JpaRepository;

import java.util.List;
import java.util.Optional;

public interface CollaboratorRepository extends JpaRepository<Collaborator, Long> {
    List<Collaborator> findByProject_Id(Long id);

    Optional<Collaborator> findByProjectAndUser(Project project, User user);
}