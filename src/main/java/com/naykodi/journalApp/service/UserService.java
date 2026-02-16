package com.naykodi.journalApp.service;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.stereotype.Service;
import com.naykodi.journalApp.Repository.UserRepositry;
import com.naykodi.journalApp.Entity.UserEntity;
import java.util.*;

@Service
public class UserService {

    @Autowired
    private UserRepositry userRepositry;

     @Autowired
    private PasswordEncoder passwordEncoder;

    public void saveEntry(UserEntity user) {
        userRepositry.save(user);
    }

    public List<UserEntity> getAllEntries() {
        return userRepositry.findAll();
    }

    public Optional<UserEntity> getByID(String myID) {
        return userRepositry.findById(myID);
    }

    public void deleteById(String myID) {
        userRepositry.deleteById(myID);
    }

    public UserEntity findByUsername(String username) {
        return userRepositry.findByUsername(username);
    }

    public UserEntity updateEntry( String  username, UserEntity user) {
        UserEntity existingUser= userRepositry.findByUsername(username);
        if (existingUser != null) {
            existingUser.setUsername(user.getUsername());
            existingUser.setPassword(passwordEncoder.encode(user.getPassword()));
            userRepositry.save(existingUser);
            return existingUser;
        } else {
            return null;
        }
    }

    public UserEntity deleteEntry(String username) {
        UserEntity existingUser = userRepositry.findByUsername(username);
        if (existingUser != null) {
            userRepositry.delete(existingUser);
            return existingUser;
        } else {
            return null;
}
    }
}