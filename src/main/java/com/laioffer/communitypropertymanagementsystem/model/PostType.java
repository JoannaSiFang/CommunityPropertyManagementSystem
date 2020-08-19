package com.laioffer.communitypropertymanagementsystem.model;

import com.fasterxml.jackson.annotation.JsonValue;

public enum PostType {

    ANNOUNCEMENT("Announcement"),
    EVENT("Event");

    private final String typeName;

    PostType(String typeName) {
        this.typeName = typeName;
    }

    @JsonValue
    public String getPostType() {
        return this.typeName;
    }
}
