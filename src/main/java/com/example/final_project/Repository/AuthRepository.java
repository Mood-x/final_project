package com.example.final_project.Repository;

import com.example.final_project.Model.User;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

import java.util.Optional;

@Repository
public interface AuthRepository extends JpaRepository<User, Integer> {
    Optional<User> findUserById(Integer id);
    Optional<User> findByUsername(String username);
}
