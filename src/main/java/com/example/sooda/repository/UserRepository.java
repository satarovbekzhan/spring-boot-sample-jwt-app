package com.example.sooda.repository;

import com.example.sooda.entity.Role;
import com.example.sooda.entity.User;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

import java.util.List;
import java.util.Optional;

@Repository
public interface UserRepository extends JpaRepository<User, String> {
    Optional<User> findByUsername(String username);

    boolean existsByUsername(String username);

    List<User> findUsersByRolesContains(Role role);
}