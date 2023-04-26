package com.example.fashionapi14.Controller;

import com.example.fashionapi14.Model.User;
import com.example.fashionapi14.Service.AdminService;
import jakarta.servlet.http.HttpServletRequest;
import jakarta.servlet.http.HttpSession;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.security.core.context.SecurityContextHolder;
import org.springframework.security.core.userdetails.UserDetails;
import org.springframework.web.bind.annotation.*;

import java.util.List;

@RestController
@RequestMapping("admin")
public class AdminController {

        private AdminService adminService;
        @Autowired
    public AdminController(AdminService adminService) {
        this.adminService = adminService;
    }

    @GetMapping("/viewUsers")
    public List<User> viewAllUsers(){
        UserDetails userDetails = (UserDetails) SecurityContextHolder.getContext().getAuthentication().getPrincipal();
        return adminService.viewAllUsers(userDetails.getUsername());
    }

    @PutMapping("/edit-role/{id}")
    public User editUserRole(@PathVariable("id") Long id,
                             @RequestParam("role") String role){
        UserDetails userDetails = (UserDetails) SecurityContextHolder.getContext().getAuthentication().getPrincipal();
        return adminService.editUserRole(id, role, userDetails.getUsername());
    }


    @PostMapping("/ban-post/{id}")
    public boolean banOrUnbanPost(@PathVariable("id")Long id){
           return adminService.banOrUnbanPost(id);
    }

    @PostMapping("/block-user/{id}")
    public User blockUser(@PathVariable("id")Long id){
        UserDetails userDetails = (UserDetails) SecurityContextHolder.getContext().getAuthentication().getPrincipal();
            return adminService.blockUser(id, userDetails.getUsername());
    };
}
