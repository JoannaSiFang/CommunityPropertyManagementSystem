package com.laioffer.communitypropertymanagementsystem.controller;

import java.time.LocalDateTime;
import java.util.List;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.security.access.prepost.PreAuthorize;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody; 
import org.springframework.web.bind.annotation.ResponseBody;

import com.laioffer.communitypropertymanagementsystem.model.Contact; 
import com.laioffer.communitypropertymanagementsystem.service.ContactService;

@Controller 
public class ContactController {
	@Autowired
	private ContactService contactService;
	
	@PostMapping(path="/contact")
	public @ResponseBody ResponseEntity addContact(@RequestBody Contact contact) { 
        contact.setTime(LocalDateTime.now());
        contactService.saveContact(contact);
        
        return new ResponseEntity(HttpStatus.OK);
	}
	
    @GetMapping("/viewcontact")
    @PreAuthorize("hasRole('ADMIN')")
    public @ResponseBody List<Contact> viewContacts() {
        return contactService.getAllContacts();
    }
	
}
