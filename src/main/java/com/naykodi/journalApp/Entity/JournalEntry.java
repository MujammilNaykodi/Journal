package com.naykodi.journalApp.Entity;

import org.springframework.data.annotation.Id;
import org.springframework.data.mongodb.core.mapping.Document;

import lombok.Data;
import lombok.NoArgsConstructor;


import java.util.Date;

@Data
@NoArgsConstructor
@Document(collection = "journal_entries")
public class JournalEntry {

    @Id
    private String id;
    private String title;
    private String content;
    private Date createdAt;


}