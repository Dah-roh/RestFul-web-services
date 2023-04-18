package com.example.fashionapi14.Service.ServiceImpl;

import com.example.fashionapi14.DTO.UserDTO;
import com.example.fashionapi14.Model.User;
import com.example.fashionapi14.Repositories.UserRepositories;
import com.example.fashionapi14.Service.UserServices;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.List;

@Service
public class UserServiceImpl implements UserServices {

    private UserRepositories userRepositories;

    @Autowired
    public UserServiceImpl( UserRepositories userRepositories){
        this.userRepositories = userRepositories;
    }




    @Override
    public User saveUser(User user){
        try{
           return userRepositories.save(user);
        }
        catch (Exception e){
            throw new RuntimeException("Cannot save user");
        }
    }


    @Override
    public String deleteUser(Long id, Long loggedInUserId){
        User loggedUser = userRepositories.findById(loggedInUserId)
                .orElseThrow(()->new NullPointerException("No such user with id: "+ loggedInUserId));
        if(loggedUser.getRole().equalsIgnoreCase("client")||loggedUser.isBlocked()){
            return "You can not delete a user. Please contact support for further help.";
        }
        userRepositories.deleteById(id);
        return "User deleted!";
    }

    @Override
    public List<User> findAll(){
        return userRepositories.findAll();
    }

    @Override
    public User logInUser(UserDTO userDTO) {
        return userRepositories.findByUsernameAndPassword(userDTO.getUsername(), userDTO.getPassword());
    }
}
