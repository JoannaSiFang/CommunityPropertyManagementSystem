package com.laioffer.communitypropertymanagementsystem.model;

import com.fasterxml.jackson.annotation.JsonValue;

public enum ServiceRequestStatus {
    PENDING("Pending"),
    ASSIGNED("Assigned"),
    COMPLETED("Completed");

    private final String typeName;

    ServiceRequestStatus(String typeName) {
        this.typeName = typeName;
    }

    @JsonValue
    public String getRequestStatus() {
        return this.typeName;
    }
}
