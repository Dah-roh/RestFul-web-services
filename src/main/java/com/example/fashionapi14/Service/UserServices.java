package com.example.fashionapi14.Service;

import com.example.fashionapi14.DTO.UserDTO;
import com.example.fashionapi14.Model.User;

import java.util.List;

public interface UserServices {
    User saveUser(User user);

    String deleteUser(Long id, Long loggedInUserId);

    List<User> findAll();

    User logInUser(UserDTO userDTO);
}
