package com.example.demo.service;

import com.example.demo.model.User;
import com.example.demo.repository.UserRepository;
import org.flywaydb.core.internal.util.JsonUtils;
import org.springframework.stereotype.Service;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.security.core.Authentication;
import org.springframework.security.core.context.SecurityContextHolder;
import org.springframework.security.crypto.password.PasswordEncoder;

@Service
public class UserServiceImpl implements UserService {

    private final UserRepository userRepository;
    private final PasswordEncoder passwordEncoder;

    @Autowired
    public UserServiceImpl(UserRepository userRepository,
                           PasswordEncoder passwordEncoder) {
        this.userRepository = userRepository;
        this.passwordEncoder = passwordEncoder;
    }

    @Override
    public User register(User user){
        user.setPassword(passwordEncoder.encode(user.getPassword()));
        return userRepository.save(user);
    }

    public User getCurrentUser(){
        Authentication authentication = SecurityContextHolder.getContext().getAuthentication();

        if(authentication == null ||!authentication.isAuthenticated()){
            throw new IllegalStateException("No authenticated user found");
        }
     Object principal = authentication.getPrincipal();

        String username;
        if(principal instanceof org.springframework.security.core.userdetails.User springUser){
            username = springUser.getUsername();
        }else{
            username = principal.toString();
        }
        return userRepository.findByLogin(username)
                .orElseThrow(() -> new IllegalArgumentException("User not found" + username));
    }

}
