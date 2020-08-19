package com.laioffer.communitypropertymanagementsystem.controller;

import com.laioffer.communitypropertymanagementsystem.model.User;
import com.laioffer.communitypropertymanagementsystem.service.UserService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.ResponseBody;

import java.util.List;

@Controller
public class UserController {
    @Autowired
    private UserService userService;

    @PostMapping(path="/adduser")
    public ResponseEntity addNewUser(@RequestBody User user) {
        userService.saveUser(user);
        return new ResponseEntity(HttpStatus.OK);
    }

    @GetMapping(path="/allusers")
    public @ResponseBody
    List<User> getAllUsers() {
        return userService.getAllUser();
    }
}
