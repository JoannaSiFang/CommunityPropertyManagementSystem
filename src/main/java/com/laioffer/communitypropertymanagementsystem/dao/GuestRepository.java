package com.laioffer.communitypropertymanagementsystem.dao;

import org.springframework.data.jpa.repository.JpaRepository;
import com.laioffer.communitypropertymanagementsystem.model.Guest;


public interface GuestRepository extends JpaRepository<Guest, Long> {

}
