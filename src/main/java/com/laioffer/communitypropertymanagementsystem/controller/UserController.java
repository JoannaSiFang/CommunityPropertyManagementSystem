package com.laioffer.communitypropertymanagementsystem.controller;

import com.laioffer.communitypropertymanagementsystem.model.User;
import com.laioffer.communitypropertymanagementsystem.service.UserService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
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
    public @ResponseBody
    User getUserById(@RequestParam(value = "userId") Integer userId) {
        return userService.getUserById(userId);
    }

    @PostMapping(path="/changepassword")
    public ResponseEntity changePassword(@RequestBody Map<String, String> params) {
        try {
            Integer userId = Integer.valueOf(params.get("User name"));
            String newPassword = params.get("new Password");
            User user = userService.getUserById(userId);
            user.setPassword(newPassword);
            userService.saveUser(user);
            return new ResponseEntity(HttpStatus.OK);
        } catch (NoSuchElementException e){
            throw new ResponseStatusException(HttpStatus.NOT_FOUND, "User Not Found", e);
        }
    }

}
