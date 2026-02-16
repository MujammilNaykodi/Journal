package com.naykodi.journalApp.Entity;

import java.util.*;

import org.springframework.data.annotation.Id;
import org.springframework.data.mongodb.core.index.Indexed;
import org.springframework.data.mongodb.core.mapping.DBRef;
import org.springframework.data.mongodb.core.mapping.Document;

import com.mongodb.lang.NonNull;

import lombok.Data;

@Data
@Document(collection = "users")
public class UserEntity {
    @Id
    private String id;
    @Indexed(unique = true)
    @NonNull
    private String username;
   @NonNull
    private String password;

    @DBRef
    List<JournalEntry> journalEntries=new ArrayList<>();

    List<String> roles ;
}
