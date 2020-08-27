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

    /***
     * Create a new service request in database
     * @param serviceRequest Json object
     * @return OK on success, error on exception
     */
    @PostMapping(path = "/makerequest")
    @PreAuthorize("hasRole('TENANT')")
    public @ResponseBody
    ResponseEntity makeRequest(@RequestBody ServiceRequest serviceRequest) {
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

    /***
     * return request of certain id. Tenant user can only view requests made by themselves.
     * @param requestId
     * @return
     */
    @GetMapping("/request")
    @PreAuthorize("hasRole('TENANT') or hasRole('ADMIN')")
    public @ResponseBody
    ServiceRequest viewRequestById(@RequestParam("id") Long requestId) {
        try {
            UserDetailsImpl userDetails = (UserDetailsImpl) SecurityContextHolder.getContext().getAuthentication().getPrincipal();
            User user = userService.getUserById(userDetails.getId());
            ServiceRequest ret = serviceRequestService.getRequestById(requestId);
            if (ret.getUser() == user || userService.isAdmin(user)) {
                return ret;
            } else {
                throw new ResponseStatusException(HttpStatus.FORBIDDEN, "You don't have privilege to view this request");
            }
        } catch (NoSuchElementException e) {
            throw new ResponseStatusException(HttpStatus.NOT_FOUND, "User Not Found!", e);
        }
    }

    /***
     * Admin can view all requests. Tenants can only view their own requests
     * @return a list of requests viewable by current user
     */
    @GetMapping(path = "/requests")
    @PreAuthorize("hasRole('TENANT') or hasRole('ADMIN')")
    public @ResponseBody
    List<ServiceRequest> getRequestsByUser() {
        try {
            UserDetailsImpl userDetails = (UserDetailsImpl) SecurityContextHolder.getContext().getAuthentication().getPrincipal();
            User user = userService.getUserById(userDetails.getId());
            if (userService.isAdmin(user)) {
                return serviceRequestService.getAllRequests();
            } else {
                return serviceRequestService.getRequestByUser(user);
            }
        } catch (NoSuchElementException e) {
            throw new ResponseStatusException(HttpStatus.NOT_FOUND, "User Not Found", e);
        }
    }

    @PostMapping(path = "/processrequest")
    @PreAuthorize("hasRole('ADMIN')")
    public @ResponseBody
    ResponseEntity processRequest(@RequestBody List<ServiceRequest> serviceRequestList) {
        try {
            UserDetailsImpl userDetails = (UserDetailsImpl) SecurityContextHolder.getContext().getAuthentication().getPrincipal();
            User user = userService.getUserById(userDetails.getId());
            for (ServiceRequest request :
                    serviceRequestList) {
                try {
                    ServiceRequest currRequest = serviceRequestService.getRequestById(request.getId());
                    currRequest.setStatus(request.getStatus());
                    currRequest.setReply(request.getReply());
                    serviceRequestService.saveRequest(currRequest);
                } catch (NoSuchElementException e) {
                    throw new ResponseStatusException(HttpStatus.NOT_FOUND, "Request Not Found", e);
                }
            }
            return new ResponseEntity(HttpStatus.OK);
        } catch (NoSuchElementException e) {
            throw new ResponseStatusException(HttpStatus.NOT_FOUND, "User Not Found", e);
        }
    }
}
