package com.laioffer.communitypropertymanagementsystem.dao;

import com.laioffer.communitypropertymanagementsystem.model.ServiceRequest;
import org.springframework.data.jpa.repository.JpaRepository;

public interface ServiceRequestRepository extends JpaRepository<ServiceRequest, Long> {
}
