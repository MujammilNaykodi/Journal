package com.naykodi.journalApp.service;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.security.core.userdetails.UserDetails;
import org.springframework.security.core.userdetails.UserDetailsService;
import org.springframework.stereotype.Component;
import org.springframework.security.core.userdetails.UsernameNotFoundException;
import org.springframework.security.core.userdetails.User;

import com.naykodi.journalApp.Entity.UserEntity;
import com.naykodi.journalApp.Repository.UserRepositry;

@Component
public class UserDetailsImplementation implements UserDetailsService {
    @Autowired
    private UserRepositry userRepositry;

    @Override
    public UserDetails loadUserByUsername(String username) {
        UserEntity user = userRepositry.findByUsername(username);
        if (user != null) {
            String[] roles = (user.getRoles() != null)
                ? user.getRoles().toArray(new String[0])
                : new String[]{"USER"}; // default role
          UserDetails userDetails = User.withUsername(user.getUsername())
                    .password(user.getPassword())
                    .roles(roles)
                    .build();
            return userDetails;
        }   
        throw new UsernameNotFoundException("User not found with username: " + username);
    }
    
}
