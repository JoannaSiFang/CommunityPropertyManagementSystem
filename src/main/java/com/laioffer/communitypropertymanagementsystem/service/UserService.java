package com.laioffer.communitypropertymanagementsystem.service;

import com.laioffer.communitypropertymanagementsystem.dao.UserRepository;
import com.laioffer.communitypropertymanagementsystem.model.User;
import com.laioffer.communitypropertymanagementsystem.model.UserRole;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.List;

@Service("userService")
public class UserService {

    @Autowired
    private UserRepository userRepository;

    public User getUserById(Long id) {
        return userRepository.findById(id).get();
    }

    public User getUserByEmail(String emailAddress) {
        return userRepository.findByEmailAddress(emailAddress).get();
    }

    public List<User> getAllUser() {
        return userRepository.findAll();
    }

    public User saveUser(User user) {
        return userRepository.save(user);
    }

    public boolean isAdmin(User user) {
        return user.getRole() == UserRole.ADMIN;
    }
}
