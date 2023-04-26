package com.example.fashionapi14.Repositories;

import com.example.fashionapi14.Model.Token;
import com.example.fashionapi14.Model.User;
import org.springframework.data.jpa.repository.JpaRepository;

import java.util.List;
import java.util.Optional;

public interface TokenRepository extends JpaRepository<Token, Long> {
    Optional<Token> findByToken(String token);
    List<Token> findAllByUser(User user);
}

