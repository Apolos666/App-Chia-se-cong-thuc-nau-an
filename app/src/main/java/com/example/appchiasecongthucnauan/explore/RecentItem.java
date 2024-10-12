package com.example.appchiasecongthucnauan.explore;

public class RecentItem {
    private String title;
    private String author;
    private String timeAgo;

    public RecentItem(String title, String author, String timeAgo) {
        this.title = title;
        this.author = author;
        this.timeAgo = timeAgo;
    }

    public String getTitle() {
        return title;
    }

    public String getAuthor() {
        return author;
    }

    public String getTimeAgo() {
        return timeAgo;
    }
}
