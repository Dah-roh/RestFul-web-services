package com.example.fashionapi14.Repositories;

import com.example.fashionapi14.Model.User;
import org.springframework.data.jpa.repository.JpaRepository;

import java.util.Optional;

public interface UserRepositories extends JpaRepository<User, Long> {
    User findByUsernameAndPassword(String username, String password);
    Optional<User> findByUsername(String username);
}
