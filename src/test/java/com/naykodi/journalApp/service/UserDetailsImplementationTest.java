package com.naykodi.journalApp.service;

import static org.mockito.ArgumentMatchers.anyString;
import static org.mockito.Mockito.when;

import java.util.List;

import org.junit.jupiter.api.Test;
import org.mockito.ArgumentMatcher;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.security.core.userdetails.UserDetails;
import org.springframework.test.context.bean.override.mockito.MockitoBean;

import com.naykodi.journalApp.Entity.UserEntity;
import com.naykodi.journalApp.Repository.UserRepositry;

@SpringBootTest
public class UserDetailsImplementationTest {

    @Autowired
    UserDetailsImplementation userDetailsImplementation;

    @MockitoBean
    UserRepositry userRepositry;

    @Test
    void loadUserByUsername() {
        when(userRepositry.findByUsername(anyString())).thenReturn(new UserEntity("testuser", "iu8u7y33tg"));
        UserDetails userDetails = userDetailsImplementation.loadUserByUsername("testuser");
    }

}
