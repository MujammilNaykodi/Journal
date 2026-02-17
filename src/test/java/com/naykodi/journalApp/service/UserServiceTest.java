package com.naykodi.journalApp.service;

import static org.junit.jupiter.api.Assertions.assertNotNull;

import org.junit.jupiter.api.Test;
import org.junit.jupiter.params.ParameterizedTest;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;
import org.junit.jupiter.params.provider.ValueSource;

import com.naykodi.journalApp.Repository.UserRepositry;

@SpringBootTest
public class UserServiceTest {

    @Autowired
    private UserRepositry userRepositry;

    @Test
    public void testFindByUsername() {
        assertNotNull(userRepositry.findByUsername("moshin"));
    }

    @ParameterizedTest
    @ValueSource(strings = {"muju1", "moshin1"})
    public void testFindByUsernameParameterized(String username) {
        assertNotNull(userRepositry.findByUsername(username));
    }
    
}
