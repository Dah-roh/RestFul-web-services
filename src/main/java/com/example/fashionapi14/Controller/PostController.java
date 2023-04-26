package com.example.fashionapi14.Controller;

import com.example.fashionapi14.Model.Post;
import com.example.fashionapi14.Service.PostService;
import jakarta.servlet.http.HttpServletRequest;
import jakarta.servlet.http.HttpSession;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.security.core.context.SecurityContextHolder;
import org.springframework.security.core.userdetails.UserDetails;
import org.springframework.web.bind.annotation.*;

import java.util.List;

@RestController
@RequestMapping("posts")
public class PostController {

    private PostService postService;

    @Autowired
    public PostController (PostService postService){
        this.postService = postService;
    }


    @PostMapping("/add-post")
    public Post savePost(@RequestBody Post post){
        System.out.println("I am making a post " + post);
        UserDetails userDetails = (UserDetails) SecurityContextHolder.getContext().getAuthentication().getPrincipal();
        return postService.savePost(post, userDetails.getUsername());
    }

    @GetMapping("/post-list")
    public List<Post> getAllPost(){
        return postService.findAll();
    }

    @GetMapping("/post/{id}")
    public Post getPost(@PathVariable("id") Long id){
        return postService.findById(id);
    }

    @PutMapping("/update/{id}")
    public Post updatePost(@PathVariable("id") Long id, @RequestBody Post post){
        return postService.updatePost(id, post);
    }

    @DeleteMapping("/delete/{id}")
    public String deletePost(@PathVariable("id") Long id, HttpServletRequest httpServletRequest){
        UserDetails userDetails = (UserDetails) SecurityContextHolder.getContext().getAuthentication().getPrincipal();
        postService.deletePost(id, userDetails.getUsername());
        return "Post successfully deleted!";
    }
}
