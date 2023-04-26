package com.example.fashionapi14.Controller;

import com.example.fashionapi14.DTO.UserDTO;
import com.example.fashionapi14.Model.Token;
import com.example.fashionapi14.Model.User;
import com.example.fashionapi14.Service.UserServices;
import jakarta.servlet.http.HttpServletRequest;
import jakarta.servlet.http.HttpSession;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.security.core.userdetails.UserDetails;
import org.springframework.web.bind.annotation.*;

import java.util.List;

@RestController
@RequestMapping("user")
public class UserController {


    private UserServices userServices;

    @Autowired
    public UserController(UserServices userServices) {
        this.userServices = userServices;
    }

    @PostMapping("/login")
    public Token homeController(@RequestBody UserDTO userDTO){
         Token token = userServices.logInUser(userDTO);
        return token;
    }

    //snake_case, kebab-case, pascalCase,
    @PostMapping("/sign-up")
    public Token signUp(@RequestBody UserDTO user){
        return userServices.saveUser(user);
    }

    @DeleteMapping("/delete/{id}")
    public String signUp(@PathVariable("id") Long id, HttpServletRequest httpServletRequest){
        HttpSession session = httpServletRequest.getSession();
        Long loggedInUser = (Long) session.getAttribute("id");
        return userServices.deleteUser(id, loggedInUser);
    }
}
