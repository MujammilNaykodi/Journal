package com.naykodi.journalApp.service;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import com.naykodi.journalApp.Repository.JournalEntryRepositry;

import lombok.extern.slf4j.Slf4j;

import com.naykodi.journalApp.Entity.JournalEntry;
import java.util.List;
import java.util.Optional;
import com.naykodi.journalApp.Entity.UserEntity;

@Service
@Slf4j
public class JournalEntryService {

    @Autowired
    private JournalEntryRepositry journalEntryRepositry;

    @Autowired
    private UserService userService;


    // @Transactional
    public void saveEntry(JournalEntry journalEntry, String username) {
        try {
            UserEntity user = userService.findByUsername(username);
            JournalEntry savedEntry = journalEntryRepositry.save(journalEntry);
            user.getJournalEntries().add(savedEntry);
            userService.saveEntry(user);
        } catch (Exception e) {
            log.error("Error saving journal entry for user {}: {}", username, e.getMessage());
            throw e; // Rethrow the exception to be handled by the caller
        }
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