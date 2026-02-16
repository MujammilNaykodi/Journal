package com.naykodi.journalApp.controller;

import java.util.List;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;
import org.springframework.security.crypto.password.PasswordEncoder;

import com.naykodi.journalApp.Entity.UserEntity;
import com.naykodi.journalApp.service.UserService;

@RestController
@RequestMapping("/admin")
public class AdminController {
    
 @Autowired
    private UserService userService;

    @Autowired
    private PasswordEncoder passwordEncoder;

     @GetMapping("/users")
    public ResponseEntity<?> getAllUsers() {
        List<UserEntity> users = userService.getAllEntries();
        if (users.isEmpty()) {
            return new ResponseEntity<>(HttpStatus.NO_CONTENT);
        } 
            return new ResponseEntity<>(users, HttpStatus.OK); 
        
    }
       @PostMapping("create-roles")
    public UserEntity createUserWithRole(@RequestBody UserEntity user) {
        user.setPassword(passwordEncoder.encode(user.getPassword()));
        user.setRoles(List.of("ADMIN"));
        userService.saveEntry(user);
        return user;
    }

   
}
