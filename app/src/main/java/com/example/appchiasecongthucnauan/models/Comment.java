package com.example.appchiasecongthucnauan.models;

public class Comment {
    private String userId;
    private String userName;
    private String content;
    private long timestamp;

    public Comment(String userId, String userName, String content) {
        this.userId = userId;
        this.userName = userName;
        this.content = content;
        this.timestamp = System.currentTimeMillis();
    }

    public String getUserId() {
        return userId;
    }

    public void setUserId(String userId) {
        this.userId = userId;
    }

    public void setUserName(String userName) {
        this.userName = userName;
    }

    public void setContent(String content) {
        this.content = content;
    }

    public void setTimestamp(long timestamp) {
        this.timestamp = timestamp;
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