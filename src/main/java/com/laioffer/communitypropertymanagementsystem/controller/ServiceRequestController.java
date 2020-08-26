package com.laioffer.communitypropertymanagementsystem.controller;

import com.laioffer.communitypropertymanagementsystem.model.ServiceRequest;
import com.laioffer.communitypropertymanagementsystem.model.User;
import com.laioffer.communitypropertymanagementsystem.security.service.UserDetailsImpl;
import com.laioffer.communitypropertymanagementsystem.service.ServiceRequestService;
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
public class ServiceRequestController {
    @Autowired
    private ServiceRequestService serviceRequestService;
    @Autowired
    private UserService userService;

    @PostMapping(path = "/makerequest")
    @PreAuthorize("hasRole('TENANT')")
    public @ResponseBody ResponseEntity makeRequest(@RequestBody ServiceRequest serviceRequest) {
        try {
            UserDetailsImpl userDetails = (UserDetailsImpl) SecurityContextHolder.getContext().getAuthentication().getPrincipal();
            User user = userService.getUserById(userDetails.getId());
            serviceRequest.setUser(user);
            serviceRequest.setTime(LocalDateTime.now());
            serviceRequestService.saveRequest(serviceRequest);
            return new ResponseEntity(HttpStatus.OK);
        } catch (NoSuchElementException e) {
            throw new ResponseStatusException(HttpStatus.NOT_FOUND, "User Not Found!", e);
        }
    }

    @GetMapping("/allrequests")
    @PreAuthorize("hasRole('TENANT') or hasRole('ADMIN')")
    public @ResponseBody
    List<ServiceRequest> viewAllRequests() {
        return serviceRequestService.getAllRequests();
    }

    @GetMapping(path = "/userrequests")
    @PreAuthorize("hasRole('TENANT')")
    public @ResponseBody
    List<ServiceRequest> getRequestsByUser(@RequestParam(value = "user") Long userId) {
        try {
            UserDetailsImpl userDetails = (UserDetailsImpl) SecurityContextHolder.getContext().getAuthentication().getPrincipal();
            User user = userService.getUserById(userDetails.getId());
            return serviceRequestService.getRequestByUser(user);
        } catch (NoSuchElementException e) {
            throw new ResponseStatusException(HttpStatus.NOT_FOUND, "User Not Found", e);
        }
    }
}
