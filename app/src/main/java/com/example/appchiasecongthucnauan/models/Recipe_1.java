package com.example.appchiasecongthucnauan.models;

public class Recipe_1 {
//    private String id;
    private String title;
    private String author;
    private String imageUrl;

    public Recipe_1(String title, String author, String imageUrl) {
//        this.id = id;
        this.title = title;
        this.author = author;
        this.imageUrl = imageUrl;
    }

    // Getters and setters
//    public String getId() { return id; }
//    public void setId(String id) { this.id = id; }
    public String getTitle() { return title; }
    public void setTitle(String title) { this.title = title; }
    public String getAuthor() { return author; }
    public void setAuthor(String author) { this.author = author; }
    public String getImageUrl() { return imageUrl; }
    public void setImageUrl(String imageUrl) { this.imageUrl = imageUrl; }
}
