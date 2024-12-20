package com.hcmute.projectCT.repository;

import com.hcmute.projectCT.model.User;
import org.springframework.data.jpa.repository.JpaRepository;

import java.util.List;
import java.util.Optional;

public interface UserRepository extends JpaRepository<User, Long> {
    User findByUsername(String username);

    Optional<User> findByEmail(String email);

    List<User> findByUsernameStartsWith(String username);

    User findByUsernameOrEmail(String username, String email);

    List<User> findByUsernameStartsWithOrNameContains(String username, String name);

    List<User> findByUsernameAndEmail(String username, String email);
}