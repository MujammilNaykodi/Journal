package com.naykodi.journalApp.controller;

import java.util.List;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import com.naykodi.journalApp.Entity.UserEntity;
import com.naykodi.journalApp.service.UserService;

@RestController
@RequestMapping("/public")
public class PublicController {
    
    @GetMapping("/health")
    public String health() {
        return "OK";
    }

    @Autowired
    private UserService userService;

     @Autowired
    private PasswordEncoder passwordEncoder;

   @PostMapping("/register")
    public UserEntity createUser(@RequestBody UserEntity user) {
        user.setPassword(passwordEncoder.encode(user.getPassword()));
        userService.saveEntry(user);
        return user;
    }

    
    @GetMapping("/users")
    public List<UserEntity> getAllUsers() {
        return userService.getAllEntries();
    }


    
}
