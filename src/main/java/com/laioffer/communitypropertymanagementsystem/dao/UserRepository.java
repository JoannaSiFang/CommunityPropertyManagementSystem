package com.laioffer.communitypropertymanagementsystem.dao;

import com.laioffer.communitypropertymanagementsystem.model.User;
import org.springframework.data.jpa.repository.JpaRepository;

public interface UserRepository extends JpaRepository<User, Integer> {

}
