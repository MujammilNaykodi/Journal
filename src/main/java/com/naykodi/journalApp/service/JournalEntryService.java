package com.naykodi.journalApp.service;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import com.naykodi.journalApp.Repository.JournalEntryRepositry;
import com.naykodi.journalApp.Entity.JournalEntry;
import java.util.List;
import java.util.Optional;
import com.naykodi.journalApp.Entity.UserEntity;

@Service
public class JournalEntryService {

    @Autowired
    private JournalEntryRepositry journalEntryRepositry;

    @Autowired
    private UserService userService;

    // @Transactional
    public void saveEntry(JournalEntry journalEntry,String username) {
UserEntity user= userService.findByUsername(username);
      JournalEntry  savedEntry = journalEntryRepositry.save(journalEntry);
        user.getJournalEntries().add(savedEntry);
        userService.saveEntry(user);
    }

    public List<JournalEntry> getAllEntries() {
        return journalEntryRepositry.findAll();
    }

    public Optional<JournalEntry> getByID(String myID) {
        return journalEntryRepositry.findById(myID);

    }

    public void deleteById(String myID) {
        journalEntryRepositry.deleteById(myID);
    }

    public JournalEntry updateEntry(String myID, JournalEntry entry) {
        JournalEntry existingEntry = journalEntryRepositry.findById(myID).orElse(null);
        if (existingEntry != null) {
            existingEntry.setTitle(entry.getTitle());
            existingEntry.setContent(entry.getContent());
            journalEntryRepositry.save(existingEntry);
            return existingEntry;
        } else {
            return null;
        }
    }
}