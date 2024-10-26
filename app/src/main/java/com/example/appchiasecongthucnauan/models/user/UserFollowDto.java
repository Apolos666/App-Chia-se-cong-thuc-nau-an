package com.example.appchiasecongthucnauan.models.user;

import java.util.UUID;

public class UserFollowDto {
    private UUID userId;
    private String name;

    // Getters and setters
    public UUID getUserId() {
        return userId;
    }

    public void setUserId(UUID userId) {
        this.userId = userId;
    }

    public String getName() {
        return name;
    }

    public void setName(String name) {
        this.name = name;
    }
}
