package com.laioffer.communitypropertymanagementsystem.controller;

import com.laioffer.communitypropertymanagementsystem.model.Post;
import com.laioffer.communitypropertymanagementsystem.model.User;
import com.laioffer.communitypropertymanagementsystem.service.PostService;
import com.laioffer.communitypropertymanagementsystem.service.UserService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.*;
import org.springframework.web.server.ResponseStatusException;

import java.time.LocalDateTime;
import java.util.List;
import java.util.NoSuchElementException;

@Controller
public class PostController {
    private static final String template = "Hello, %s!";

    @Autowired
    private PostService postService;
    @Autowired
    private UserService userService;

    @PostMapping("/makepost")
    public @ResponseBody
    ResponseEntity makePost(@RequestParam(value="user") Integer userId, @RequestBody Post post) {
        try {
            User user = userService.getUserById(userId);
            if (userService.isAdmin(user)) {
                post.setUser(user);
                post.setTime(LocalDateTime.now());
                postService.savePost(post);
                return new ResponseEntity(HttpStatus.OK);
            } else {
                return new ResponseEntity(HttpStatus.FORBIDDEN);
            }
        } catch (NoSuchElementException e){
            throw new ResponseStatusException(HttpStatus.NOT_FOUND, "User Not Found", e);
        }
    }

    @GetMapping("/posts")
    public @ResponseBody List<Post> viewPosts() {
        return postService.getAllPosts();
    }
}
