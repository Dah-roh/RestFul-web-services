package com.example.fashionapi14.Service;

import com.example.fashionapi14.DTO.UserDTO;
import com.example.fashionapi14.Model.Token;
import com.example.fashionapi14.Model.User;
import org.springframework.security.core.userdetails.UserDetails;

import java.util.List;

public interface UserServices {
    Token saveUser(UserDTO user);

    String deleteUser(Long id, Long loggedInUserId);

    List<User> findAll();

    Token logInUser(UserDTO userDTO);
}
