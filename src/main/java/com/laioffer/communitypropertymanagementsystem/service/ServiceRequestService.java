package com.laioffer.communitypropertymanagementsystem.service;

import com.laioffer.communitypropertymanagementsystem.dao.ServiceRequestRepository;
import com.laioffer.communitypropertymanagementsystem.model.ServiceRequest;
import com.laioffer.communitypropertymanagementsystem.model.User;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.List;
import java.util.Queue;

@Service("requestService")
public class ServiceRequestService {
    @Autowired
    private ServiceRequestRepository serviceRequestRepository;

    public ServiceRequest saveRequest(ServiceRequest serviceRequest) {
        return serviceRequestRepository.save(serviceRequest);
    }

    public ServiceRequest getRequestById(Long id) {
        return serviceRequestRepository.getOne(id);
    }

    public List<ServiceRequest> getAllRequests() {
        return serviceRequestRepository.findAll();
    }

    public List<ServiceRequest> getRequestByUser(User user) {
        return serviceRequestRepository.findAll();
//        Queue query =
    }
}
