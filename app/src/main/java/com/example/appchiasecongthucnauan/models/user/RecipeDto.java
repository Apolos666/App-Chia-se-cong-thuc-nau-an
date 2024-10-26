package com.example.appchiasecongthucnauan.models.user;

import java.util.List;
import java.util.UUID;

public class RecipeDto {
    private UUID id;
    private String title;
    private List<String> mediaUrls;

    // Getters and setters
    public UUID getId() {
        return id;
    }

    public void setId(UUID id) {
        this.id = id;
    }

    public String getTitle() {
        return title;
    }

    public void setTitle(String title) {
        this.title = title;
    }

    public List<String> getMediaUrls() {
        return mediaUrls;
    }

    public void setMediaUrls(List<String> mediaUrls) {
        this.mediaUrls = mediaUrls;
    }
}
