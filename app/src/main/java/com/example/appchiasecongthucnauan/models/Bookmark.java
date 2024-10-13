package com.example.appchiasecongthucnauan.models;

public class Bookmark {
    private String title;
    private String author;

    public Bookmark(String title, String author) {
        this.title = title;
        this.author = author;
    }

    public String getTitle() {
        return title;
    }

    public String getAuthor() {
        return author;
    }
}
