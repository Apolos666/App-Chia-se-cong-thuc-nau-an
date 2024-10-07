package com.example.appchiasecongthucnauan.models;

public class Comment {
    private String userName;
    private String content;
    private long timestamp;

    public Comment(String userName, String content) {
        this.userName = userName;
        this.content = content;
        this.timestamp = System.currentTimeMillis();
    }

    public String getUserName() {
        return userName;
    }

    public String getContent() {
        return content;
    }

    public long getTimestamp() {
        return timestamp;
    }
}