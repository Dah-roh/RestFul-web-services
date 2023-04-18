package com.example.fashionapi14.Repositories;

import com.example.fashionapi14.Model.Post;
import org.springframework.data.jpa.repository.JpaRepository;

public interface PostRepositories extends JpaRepository<Post, Long> {
}
