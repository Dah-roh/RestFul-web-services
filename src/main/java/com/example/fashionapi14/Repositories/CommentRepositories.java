package com.example.fashionapi14.Repositories;

import com.example.fashionapi14.Model.Comment;
import org.springframework.data.jpa.repository.JpaRepository;

public interface CommentRepositories extends JpaRepository<Comment, Long> {
}
