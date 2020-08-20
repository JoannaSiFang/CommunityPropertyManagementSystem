package com.laioffer.communitypropertymanagementsystem.model;

import com.fasterxml.jackson.annotation.JsonValue;

public enum ServiceRequestCategory {
    COMMONAREA("Common Areas"),
    APPLIANCE("House Appliance");

    private final String typeName;

    ServiceRequestCategory(String typeName) {
        this.typeName = typeName;
    }

    @JsonValue
    public String getRequestType() {
        return this.typeName;
    }
}
