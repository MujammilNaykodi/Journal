package com.naykodi.journalApp.Repository;
import org.springframework.data.mongodb.repository.MongoRepository;
import com.naykodi.journalApp.Entity.UserEntity;


public interface UserRepositry   extends MongoRepository<UserEntity, String> {

    UserEntity findByUsername(String username);
} 
