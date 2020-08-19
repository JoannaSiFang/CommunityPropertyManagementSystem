package com.laioffer.communitypropertymanagementsystem.model;

import com.fasterxml.jackson.annotation.JsonValue;

public enum UserRole {

    ADMIN("admin"),
    TENANT("tenant");

    private final String role;

    UserRole(String role) {
        this.role = role;
    }

    @JsonValue
    public String getUserRole() {
        return this.role;
    }
}
