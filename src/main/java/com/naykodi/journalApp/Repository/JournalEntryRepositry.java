package com.naykodi.journalApp.Repository;

import org.springframework.data.mongodb.repository.MongoRepository;
import com.naykodi.journalApp.Entity.JournalEntry;

public interface JournalEntryRepositry extends MongoRepository<JournalEntry, String> {

}
