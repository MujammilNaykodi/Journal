package com.naykodi.journalApp.controller;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.security.core.Authentication;
import org.springframework.security.core.context.SecurityContext;
import org.springframework.security.core.context.SecurityContextHolder;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.web.bind.annotation.DeleteMapping;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.PutMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;
import org.springframework.web.service.annotation.PutExchange;
import org.springframework.web.servlet.mvc.method.annotation.ResponseEntityExceptionHandler;

import java.security.Security;
import java.util.*;
import com.naykodi.journalApp.Entity.UserEntity;

import com.naykodi.journalApp.service.UserService;

@RestController
@RequestMapping("/users")
public class UserController {

    @Autowired
    private PasswordEncoder passwordEncoder;
    @Autowired
    private UserService userService;

 

    // @PostMapping
    // public UserEntity createUserWithRole(@RequestBody UserEntity user) {
    //     user.setPassword(passwordEncoder.encode(user.getPassword()));
    //     user.setRoles(List.of("USER"));
    //     userService.saveEntry(user);
    //     return user;
    // }

    @PutMapping()
    public ResponseEntity<UserEntity> updateUser( @RequestBody UserEntity user) {

        Authentication auth = SecurityContextHolder.getContext().getAuthentication();;
        String username = auth.getName();
        UserEntity updatedUser = userService.updateEntry(username, user);
        if (updatedUser != null) {
            return new ResponseEntity<>(updatedUser, HttpStatus.OK);
        } else {
            return new ResponseEntity<>(HttpStatus.NOT_FOUND);
        }
    }

   @DeleteMapping()
public ResponseEntity<?> deleteUser() {
    Authentication auth = SecurityContextHolder.getContext().getAuthentication();;
    String username = auth.getName();
    UserEntity user = userService.deleteEntry(username);

    if (user != null) {
        return new ResponseEntity<>(user, HttpStatus.OK);
    } 

    return new ResponseEntity<>(HttpStatus.NOT_FOUND);
}


}
