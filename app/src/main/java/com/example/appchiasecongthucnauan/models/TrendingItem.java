package com.example.appchiasecongthucnauan.models;

public class TrendingItem {
    private String title;
    private String author;
    private String imageUrl;

    public TrendingItem(String title, String author, String imageUrl) {
        this.title = title;
        this.author = author;
        this.imageUrl = imageUrl;
    }

    public String getTitle() {
        return title;
    }

    public String getAuthor() {
        return author;
    }

    public String getImageUrl() {
        return imageUrl;
    }
}

