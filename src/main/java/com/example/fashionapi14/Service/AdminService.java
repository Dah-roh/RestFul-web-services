package com.example.fashionapi14.Service;

import com.example.fashionapi14.Model.User;
import org.springframework.beans.factory.annotation.Autowired;

import java.util.List;

public interface AdminService {

    List<User> viewAllUsers(Long id);

    User editUserRole(Long id, String newRole, Long loggedInUserId);

    boolean banOrUnbanPost(Long id);

    User blockUser(Long id, Long loggedInUserId);
}
