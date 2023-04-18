package com.example.fashionapi14.Controller;

import com.example.fashionapi14.DTO.UserDTO;
import com.example.fashionapi14.Model.User;
import com.example.fashionapi14.Service.UserServices;
import jakarta.servlet.http.HttpServletRequest;
import jakarta.servlet.http.HttpSession;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.*;

import java.util.List;

@RestController
@RequestMapping("/user")
public class UserController {


    private UserServices userServices;

    @Autowired
    public UserController(UserServices userServices) {
        this.userServices = userServices;
    }

    @PostMapping("/login")
    public User homeController(@RequestBody UserDTO userDTO, HttpServletRequest httpServletRequest){
         User user = userServices.logInUser(userDTO);
        HttpSession session = httpServletRequest.getSession();
        session.setAttribute("id", user.getId());
        return user;
    }

    //snake_case, kebab-case, pascalCase,
    @PostMapping("/sign-up")
    public User signUp(@RequestBody User user){
        //TODO:Check null values
        return userServices.saveUser(user);
    }

    @DeleteMapping("/delete/{id}")
    public String signUp(@PathVariable("id") Long id, HttpServletRequest httpServletRequest){
        HttpSession session = httpServletRequest.getSession();
        Long loggedInUser = (Long) session.getAttribute("id");
        return userServices.deleteUser(id, loggedInUser);
    }
}
