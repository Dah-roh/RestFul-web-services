package com.example.fashionapi14.Controller;

import com.example.fashionapi14.Model.Post;
import com.example.fashionapi14.Service.PostService;
import jakarta.servlet.http.HttpServletRequest;
import jakarta.servlet.http.HttpSession;
import org.springframework.beans.factory.annotation.Autowired;
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
    public Post savePost(@RequestBody Post post, HttpServletRequest httpServletRequest){
        HttpSession session = httpServletRequest.getSession();
        Long id = (Long) session.getAttribute("id");
        return postService.savePost(post, id);
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
        HttpSession session = httpServletRequest.getSession();
        Long loggedInUserId = (Long) session.getAttribute("id");
        postService.deletePost(id, loggedInUserId);
        return "Post successfully deleted!";
    }
}
