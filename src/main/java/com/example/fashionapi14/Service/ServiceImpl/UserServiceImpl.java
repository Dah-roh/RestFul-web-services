package com.example.fashionapi14.Service.ServiceImpl;

import com.example.fashionapi14.DTO.UserDTO;
import com.example.fashionapi14.Model.Token;
import com.example.fashionapi14.Model.User;
import com.example.fashionapi14.Repositories.TokenRepository;
import com.example.fashionapi14.Repositories.UserRepositories;
import com.example.fashionapi14.Service.UserServices;
import com.example.fashionapi14.Utils.JwtUtils;
import jakarta.transaction.Transactional;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.security.core.userdetails.UserDetails;
import org.springframework.security.core.userdetails.UserDetailsService;
import org.springframework.security.core.userdetails.UsernameNotFoundException;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.stereotype.Service;

import java.util.List;

@Service
public class UserServiceImpl implements UserServices, UserDetailsService {

    private UserRepositories userRepositories;
    private PasswordEncoder passwordEncoder;
    private TokenRepository tokenRepository;
    private JwtUtils jwtUtils;

    @Autowired
    public UserServiceImpl(UserRepositories userRepositories, PasswordEncoder passwordEncoder, TokenRepository tokenRepository, JwtUtils jwtUtils){
        this.userRepositories = userRepositories;
        this.passwordEncoder = passwordEncoder;
        this.tokenRepository = tokenRepository;
        this.jwtUtils = jwtUtils;
    }




    @Override
    @Transactional(rollbackOn = Exception.class)
    public Token saveUser(UserDTO user){
        try{
            User saveUser = new User(user);
            saveUser.setPassword(passwordEncoder.encode(saveUser.getPassword()));
            User user1 = userRepositories.save(saveUser);
            UserDetails userDetails = loadUserByUsername(user1.getUsername());
            String token =jwtUtils.generateToken(userDetails);
             Token tokenToSave = Token.builder()
                    .token(token)
                    .user(user1)
                     .isExpired(false)
                     .isRevoked(false)
                     .build();
             return tokenRepository.save(tokenToSave);
        }
        catch (Exception e){
            throw new RuntimeException("Cannot save user "+ e.getMessage());
        }
    }


    public void revokeAllTokensForUser(User user){
        List<Token> tokens = tokenRepository.findAllByUser(user);
        tokens.stream().forEach(token->{
            token.setRevoked(true);
            token.setExpired(true);
        });
        tokenRepository.saveAll(tokens);
    }

    @Override
    public String deleteUser(Long id, Long loggedInUserId){
        User loggedUser = userRepositories.findById(loggedInUserId)
                .orElseThrow(()->new NullPointerException("No such user with id: "+ loggedInUserId));
        if(loggedUser.getRole().name().equalsIgnoreCase("client")||loggedUser.isBlocked()){
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
    @Transactional(rollbackOn = Exception.class)
    public Token logInUser(UserDTO userDTO) {
        User user = userRepositories.findByUsername(userDTO.getUsername())
                .orElseThrow();
        UserDetails userDetails = loadUserByUsername(userDTO.getUsername());
        revokeAllTokensForUser(user);
        String token  = jwtUtils.generateToken(userDetails);
        Token tokenToSave = Token.builder()
                .token(token)
                .user(user)
                .isExpired(false)
                .isRevoked(false)
                .build();
         tokenRepository.save(tokenToSave);
                return tokenToSave;

    }

    @Override
    public UserDetails loadUserByUsername(String username) throws UsernameNotFoundException {
        UserDetails userDetails = userRepositories.findByUsername(username)
                .orElseThrow(()->new UsernameNotFoundException("No user with username: "+ username));
        return userDetails;
    }
}
