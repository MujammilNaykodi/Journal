package com.naykodi.journalApp.controller;

import java.lang.StackWalker.Option;
import java.net.Authenticator;
import java.security.Security;
import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Optional;
import java.util.Date;

import org.springframework.security.core.Authentication;
import org.jspecify.annotations.Nullable;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.security.core.context.SecurityContextHolder;
import org.springframework.web.bind.annotation.DeleteMapping;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.PutMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;
import org.springframework.web.service.annotation.PutExchange;
import org.springframework.web.servlet.mvc.method.annotation.ResponseEntityExceptionHandler;
import com.naykodi.journalApp.Entity.JournalEntry;
import com.naykodi.journalApp.Entity.UserEntity;
import com.naykodi.journalApp.service.JournalEntryService;
import com.naykodi.journalApp.service.UserService;

@RestController
@RequestMapping("/journal")
public class JournalEntryController {
    @Autowired
    private JournalEntryService journalEntryService;

    @Autowired
    private UserService userService;

    @GetMapping("journalEntries")
    public List<JournalEntry> getAllEntries() {
        return journalEntryService.getAllEntries();
    }

    @GetMapping()
    public List<JournalEntry> getAllEntriesOfUser() {
       Authentication auth = SecurityContextHolder.getContext().getAuthentication();;
    String username = auth.getName();
       UserEntity user = userService.findByUsername(username);
       List<JournalEntry> entries = user.getJournalEntries();
        return entries;

    }

    @GetMapping("id/{myID}")
    public ResponseEntity<JournalEntry> getEntryById(@PathVariable String myID) {
         Authentication auth = SecurityContextHolder.getContext().getAuthentication();;
          String username = auth.getName();
            UserEntity user = userService.findByUsername(username);
             Optional<JournalEntry> entry = user.getJournalEntries().stream().filter(e -> e.getId().equals(myID)).findFirst();
        if(entry.isPresent()) {
            return  new ResponseEntity<>(entry.get(), HttpStatus.OK);
        }

            return  new ResponseEntity<>(HttpStatus.NOT_FOUND);
    }

    @PostMapping()
    public ResponseEntity<JournalEntry> createEntry(@RequestBody JournalEntry entry) {
        try{
             Authentication auth = SecurityContextHolder.getContext().getAuthentication();;
    String username = auth.getName();
            entry.setCreatedAt(new Date());
        journalEntryService.saveEntry(entry,username);
        return new ResponseEntity<>(entry, HttpStatus.CREATED);
        } catch (Exception e) {
            return new ResponseEntity<>( HttpStatus.INTERNAL_SERVER_ERROR);
        }
    }

    @PutMapping("id/{myID}")
    public ResponseEntity<JournalEntry> updateEntry(@PathVariable String myID, @RequestBody JournalEntry entry) {
        Authentication auth = SecurityContextHolder.getContext().getAuthentication();;
        String username = auth.getName();    
            UserEntity user = userService.findByUsername(username);
             Optional<JournalEntry> existingEntry = user.getJournalEntries().stream().filter(e -> e.getId().equals(myID)).findFirst();
        if(existingEntry.isPresent()) {
            JournalEntry updatedEntry = journalEntryService.updateEntry(myID, entry);
            return new ResponseEntity<>(updatedEntry, HttpStatus.OK);
        } else {
            return new ResponseEntity<>(HttpStatus.NOT_FOUND);
        }

    }

    @DeleteMapping("id/{myID}")
    public ResponseEntity<JournalEntry> deleteEntryById(@PathVariable String myID) {
        Optional<JournalEntry> JournalEntry=journalEntryService.getByID(myID);
        if(JournalEntry.isPresent()) {
            journalEntryService.deleteById(myID);
            return new ResponseEntity<>(JournalEntry.get(), HttpStatus.OK);
        }
        return new ResponseEntity<>(HttpStatus.NOT_FOUND);

    }
}
