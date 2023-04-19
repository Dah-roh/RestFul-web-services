package com.example.fashionapi14.Service.ServiceImpl;

import com.example.fashionapi14.Model.Post;
import com.example.fashionapi14.Model.User;
import com.example.fashionapi14.Repositories.PostRepositories;
import com.example.fashionapi14.Repositories.UserRepositories;
import com.example.fashionapi14.Service.PostService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.List;

@Service
public class PostServiceImpl implements PostService {

    private PostRepositories postRepositories;
    private UserRepositories userRepositories;
    @Autowired
    public PostServiceImpl(PostRepositories postRepositories, UserRepositories userRepositories) {
        this.postRepositories = postRepositories;
        this.userRepositories = userRepositories;
    }


    @Override
    public Post savePost(Post post, Long id) {
        User user = userRepositories.findById(id)
                .orElseThrow(()->
                        new NullPointerException("No user found with id: "+ id));
//        if (user.getRole().equals("admin")) {
//            post.setUser(user);
//            return postRepositories.save(post);
//        }
        post.setUser(user);
        return postRepositories.save(post);
    }

    @Override
    public List<Post> findAll() {
        return postRepositories.findAll();
    }

    @Override
    public Post findById(Long id){
        return postRepositories.findById(id)
                .orElseThrow(()->
                        new NullPointerException("No post found with id: "+ id));
    }

    @Override
    public Post updatePost(Long id, Post post) {
        Post existingPost = postRepositories.findById(id)
                .orElseThrow(()->
                        new NullPointerException("No post found with id: "+ id));
        existingPost.setPost(post.getPost());
        existingPost.setTitle(post.getTitle());
        return postRepositories.save(existingPost);
    }


//TODO: DELETE POST UNIQUE TO USER
    @Override
    public void deletePost(Long id, Long loggedInUserId) {
        User loggedUser = userRepositories.findById(loggedInUserId)
                .orElseThrow(()->
                        new NullPointerException("No user found with id: "+ id));
        if(loggedUser.isBlocked()){
            throw new RuntimeException("You do not have the right privilege to complete this action: DELETE POST");
        }
        postRepositories.deleteById(id);
    }
}
