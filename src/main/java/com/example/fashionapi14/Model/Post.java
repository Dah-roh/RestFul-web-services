package com.example.fashionapi14.Model;

import com.example.fashionapi14.DTO.PostDTO;
import jakarta.persistence.*;
import lombok.Data;

import java.util.List;

@Entity
@Data
public class Post {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;
    private String title;
    private String post;
    private Long likes;
    private Long dislike;
    @ManyToOne
    private User user;
    @OneToMany
    private List<Comment> comment;

    public Post(PostDTO postDTO) {
        this.title = postDTO.getTitle();
        this.post = postDTO.getPost();
    }
}
