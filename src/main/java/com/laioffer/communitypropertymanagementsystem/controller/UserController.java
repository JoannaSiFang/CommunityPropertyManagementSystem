package com.laioffer.communitypropertymanagementsystem.controller;

import com.laioffer.communitypropertymanagementsystem.model.User;
import com.laioffer.communitypropertymanagementsystem.security.service.UserDetailsImpl;
import com.laioffer.communitypropertymanagementsystem.service.UserService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.security.access.prepost.PreAuthorize;
import org.springframework.security.core.context.SecurityContextHolder;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.*;
import org.springframework.web.server.ResponseStatusException;

import java.util.List;
import java.util.Map;
import java.util.NoSuchElementException;

@Controller
public class UserController {
    @Autowired
    private UserService userService;
    @Autowired
    private PasswordEncoder encoder;

    @PostMapping(path="/adduser")
    public ResponseEntity addNewUser(@RequestBody User user) {
        user.setPassword(encoder.encode(user.getPassword()));
        userService.saveUser(user);
        return new ResponseEntity(HttpStatus.OK);
    }

    @GetMapping(path="/allusers")
    public @ResponseBody List<User> getAllUsers() {
        return userService.getAllUser();
    }

    @GetMapping(path="/profile")
    @PreAuthorize("hasRole('TENANT')")
    public @ResponseBody User getUserProfile() {
        UserDetailsImpl userDetails = (UserDetailsImpl) SecurityContextHolder.getContext().getAuthentication().getPrincipal();
        User user = userService.getUserById(userDetails.getId());
        return user;
    }

    @PostMapping(path="/changepassword")
    @PreAuthorize("hasRole('TENANT')")
    public ResponseEntity changePassword(@RequestBody Map<String, String> params) {
        try {
            UserDetailsImpl userDetails = (UserDetailsImpl) SecurityContextHolder.getContext().getAuthentication().getPrincipal();
            User user = userService.getUserById(userDetails.getId());
            String userName = params.get("userName");
            if (userService.validUser(userName, user)) {
                String newPassword = params.get("newPassword");
                user.setPassword(encoder.encode(newPassword));
                userService.saveUser(user);
                return new ResponseEntity(HttpStatus.OK);
            } else {
                return new ResponseEntity(HttpStatus.FORBIDDEN);
            }
        } catch (NoSuchElementException e){
            throw new ResponseStatusException(HttpStatus.NOT_FOUND, "User Not Found!", e);
        }
    }

}
