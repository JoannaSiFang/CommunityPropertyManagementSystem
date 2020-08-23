package com.laioffer.communitypropertymanagementsystem.service;

import java.util.List;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import com.laioffer.communitypropertymanagementsystem.dao.ContactRepository;
import com.laioffer.communitypropertymanagementsystem.model.Contact;

@Service("contactService")
public class ContactService {

    @Autowired
    private ContactRepository contactRepository;

    public Contact saveContact(Contact contact) {
        return contactRepository.save(contact);
    }

    public List<Contact> getAllContacts() {
        return contactRepository.findAll();
    }
    
    public Contact getContactById(Long id) {
        return contactRepository.findById(id).get();
    }
}