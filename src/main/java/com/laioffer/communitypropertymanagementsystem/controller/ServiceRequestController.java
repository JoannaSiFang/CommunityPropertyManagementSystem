package com.laioffer.communitypropertymanagementsystem.controller;

import com.laioffer.communitypropertymanagementsystem.model.ServiceRequest;
import com.laioffer.communitypropertymanagementsystem.model.User;
import com.laioffer.communitypropertymanagementsystem.service.ServiceRequestService;
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
public class ServiceRequestController {
    @Autowired
    private ServiceRequestService serviceRequestService;
    @Autowired
    private UserService userService;

    @PostMapping(path = "/makeRequest")
    public @ResponseBody
    ResponseEntity makeRequest(@RequestParam(value = "user") Integer userId, @RequestBody ServiceRequest serviceRequest) {
        try {
            User user = userService.getUserById(userId);
            if (userService.isAdmin(user)) {
                serviceRequest.setUser(user);
                serviceRequest.setTime(LocalDateTime.now());
                serviceRequestService.saveRequest(serviceRequest);
                return new ResponseEntity(HttpStatus.OK);
            } else {
                return new ResponseEntity(HttpStatus.FORBIDDEN);
            }
        } catch (NoSuchElementException e) {
            throw new ResponseStatusException(HttpStatus.NOT_FOUND, "User Not Found", e);
        }
    }

    @GetMapping("/allrequests")
    public @ResponseBody
    List<ServiceRequest> viewAllRequests() {
        return serviceRequestService.getAllRequests();
    }

    @GetMapping(path = "/userrequests")
    public @ResponseBody
    List<ServiceRequest> getRequestsByUser(@RequestParam(value = "user") Integer userId) {
        try {
            User user = userService.getUserById(userId);
            if (!userService.isAdmin(user)) {
                return serviceRequestService.getRequestByUser(user);
            } else {
                return null;
            }
        } catch (NoSuchElementException e) {
            throw new ResponseStatusException(HttpStatus.NOT_FOUND, "User Not Found", e);
        }
    }
}
