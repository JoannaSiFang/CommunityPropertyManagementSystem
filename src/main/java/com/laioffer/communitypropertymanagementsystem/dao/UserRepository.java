package com.laioffer.communitypropertymanagementsystem.dao;

import com.laioffer.communitypropertymanagementsystem.model.User;
import org.springframework.data.jpa.repository.JpaRepository;

import java.util.Optional;

public interface UserRepository extends JpaRepository<User, Long> {

    Optional<User> findByEmailAddress(String emailAddress);

    Boolean existsByEmailAddress(String emailAddress);
}
