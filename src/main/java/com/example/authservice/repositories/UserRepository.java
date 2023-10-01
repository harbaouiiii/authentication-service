package com.example.authservice.repositories;

import com.example.authservice.entities.Role;
import com.example.authservice.entities.User;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

import java.util.List;
import java.util.Optional;
import java.util.UUID;

@Repository
public interface UserRepository extends JpaRepository<User, UUID> {
    Optional<User> findByUserName(String userName);
    Optional<User> findByEmail(String email);
    List<User> findUsersByRole(Role role);
    Boolean existsByUserName(String userName);
    Boolean existsByEmail(String email);
}
