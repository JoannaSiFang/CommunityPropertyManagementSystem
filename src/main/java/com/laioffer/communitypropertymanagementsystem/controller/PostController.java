package com.laioffer.communitypropertymanagementsystem.controller;

import com.laioffer.communitypropertymanagementsystem.model.Post;
import com.laioffer.communitypropertymanagementsystem.model.User;
import com.laioffer.communitypropertymanagementsystem.security.service.UserDetailsImpl;
import com.laioffer.communitypropertymanagementsystem.service.PostService;
import com.laioffer.communitypropertymanagementsystem.service.UserService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.security.access.prepost.PreAuthorize;
import org.springframework.security.core.context.SecurityContextHolder;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.*;
import org.springframework.web.server.ResponseStatusException;

import java.time.LocalDateTime;
import java.util.List;
import java.util.NoSuchElementException;

@Controller
@CrossOrigin(origins = "*", maxAge = 3600)
public class PostController {
    @Autowired
    private PostService postService;
    @Autowired
    private UserService userService;

    @PostMapping("/makepost")
    @PreAuthorize("hasRole('ADMIN')")
    public @ResponseBody ResponseEntity makePost(@RequestBody Post post) {
        try {
            UserDetailsImpl userDetails = (UserDetailsImpl) SecurityContextHolder.getContext().getAuthentication().getPrincipal();
            User user = userService.getUserById(userDetails.getId());
            post.setUser(user);
            post.setTime(LocalDateTime.now());
            postService.savePost(post);
            return new ResponseEntity(HttpStatus.OK);
        } catch (NoSuchElementException e){
            throw new ResponseStatusException(HttpStatus.NOT_FOUND, "User Not Found!", e);
        }
    }

    @GetMapping("/posts")
    public @ResponseBody List<Post> viewPosts() {
        return postService.getAllPosts();
    }
}
