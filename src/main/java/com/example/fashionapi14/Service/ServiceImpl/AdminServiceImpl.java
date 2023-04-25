package com.example.fashionapi14.Service.ServiceImpl;

import com.example.fashionapi14.Enums.Role;
import com.example.fashionapi14.Model.Post;
import com.example.fashionapi14.Model.User;
import com.example.fashionapi14.Repositories.PostRepositories;
import com.example.fashionapi14.Repositories.UserRepositories;
import com.example.fashionapi14.Service.AdminService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.List;

@Service
public class AdminServiceImpl implements AdminService {

    private UserRepositories userRepositories;
    private PostRepositories postRepositories;


    @Autowired
    public AdminServiceImpl(UserRepositories userRepositories, PostRepositories postRepositories) {
        this.userRepositories = userRepositories;
        this.postRepositories = postRepositories;
    }



    @Override
    public List<User> viewAllUsers(Long id){
        User loggedInUser = userRepositories.findById(id)
                .orElseThrow(()-> new NullPointerException("No such user with id: "+ id));
        if (loggedInUser.getRole().name().equalsIgnoreCase("admin")){
            return userRepositories.findAll();
        }
        throw new RuntimeException("You have no admin privileges");
    }

    @Override
    public User editUserRole(Long id, String newRole, Long loggedInUserId){
        String role = newRole.toLowerCase();
//        if (!role.equalsIgnoreCase("client")||
//                !role.equalsIgnoreCase("admin")){
//            throw new RuntimeException("No such user role as :"+ role+", for User role (client or admin)");
//        }
        User loggedInUser = userRepositories.findById(loggedInUserId)
                .orElseThrow(()-> new NullPointerException("No such user with id: "+ loggedInUserId));

        User existingUser = userRepositories.findById(id)
                .orElseThrow(()-> new NullPointerException("No such user with id: "+ id));
        if (!loggedInUser.getRole().name().equalsIgnoreCase("admin")){
            throw new RuntimeException("You have no user role edit privileges for role: "+ newRole);
        }
        if(loggedInUser.getRole().name().equalsIgnoreCase("admin")) {
            existingUser.setRole(Role.valueOf(newRole));
        }
        return userRepositories.save(existingUser);
    }

    @Override
    public boolean banOrUnbanPost(Long id){
        Post post = postRepositories.findById(id).
                orElseThrow(()-> new NullPointerException("No such user with id: "+ id));
        post.setBanned(!post.isBanned());
        postRepositories.save(post);
        return post.isBanned();
    }

    @Override
    public User blockUser(Long id, Long loggedInUserId) {
        User loggedInUser = userRepositories.findById(loggedInUserId)
                .orElseThrow(()-> new NullPointerException("No such user with id: "+ loggedInUserId));
        User editRoleUser = userRepositories.findById(id)
                .orElseThrow(()-> new NullPointerException("No such user with id: "+ id));
        if (loggedInUser.getRole().name().equalsIgnoreCase("admin")){

            editRoleUser.setBlocked(!editRoleUser.isBlocked());
            userRepositories.save(editRoleUser);
            return editRoleUser;
        }
        throw new RuntimeException("You have no user blocking privileges with id: "+ loggedInUserId);    }

}
