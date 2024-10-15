package com.example.appchiasecongthucnauan.models;

public class User {
//    private String id;
    private String name;
    private String username;
    private String avatarUrl;

    public User(String name, String username, String avatarUrl) {
//        this.id = id;
        this.name = name;
        this.username = username;
        this.avatarUrl = avatarUrl;
    }

    // Getters and setters
//    public String getId() { return id; }
//    public void setId(String id) { this.id = id; }
    public String getName() { return name; }
    public void setName(String name) { this.name = name; }
    public String getUsername() { return username; }
    public void setUsername(String username) { this.username = username; }
    public String getAvatarUrl() { return avatarUrl; }
    public void setAvatarUrl(String avatarUrl) { this.avatarUrl = avatarUrl; }
}