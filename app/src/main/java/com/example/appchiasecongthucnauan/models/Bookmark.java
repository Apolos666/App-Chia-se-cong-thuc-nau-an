package com.example.appchiasecongthucnauan.models;

public class Bookmark {
    private String title;
    private String author;
    private String mediaUrl;
    private String recipeId;

    public Bookmark(String title, String author, String mediaUrl, String recipeId) {
        this.title = title;
        this.author = author;
        this.mediaUrl = mediaUrl;
        this.recipeId = recipeId;
    }

    public String getTitle() {
        return title;
    }

    public String getAuthor() {
        return author;
    }

    public String getMediaUrl() {
        return mediaUrl;
    }

    public String getRecipeId() {
        return recipeId;
    }
}
