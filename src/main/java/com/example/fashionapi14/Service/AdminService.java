package com.example.fashionapi14.Service;

import com.example.fashionapi14.Model.User;
import org.springframework.beans.factory.annotation.Autowired;

import java.util.List;

public interface AdminService {

    List<User> viewAllUsers(String username);

    User editUserRole(Long id, String newRole, String username);

    boolean banOrUnbanPost(Long id);

    User blockUser(Long id, String username);
}
