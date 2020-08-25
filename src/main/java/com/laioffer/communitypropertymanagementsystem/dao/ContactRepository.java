package com.laioffer.communitypropertymanagementsystem.dao;

import org.springframework.data.jpa.repository.JpaRepository;
import com.laioffer.communitypropertymanagementsystem.model.Contact;


public interface ContactRepository extends JpaRepository<Contact, Long> {

}
