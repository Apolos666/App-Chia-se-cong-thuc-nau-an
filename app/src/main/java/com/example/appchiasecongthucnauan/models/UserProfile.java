package com.example.appchiasecongthucnauan.models;

public class UserProfile {
    private String name;
    private String username;
    private String email;
    private String bio;
    private String website;
    private String socialMedia;

    public UserProfile(String name, String username, String email, String bio, String website, String socialMedia) {
        this.name = name;
        this.username = username;
        this.email = email;
        this.bio = bio;
        this.website = website;
        this.socialMedia = socialMedia;
    }

    // Getters
    public String getName() { return name; }
    public String getUsername() { return username; }
    public String getEmail() { return email; }
    public String getBio() { return bio; }
    public String getWebsite() { return website; }
    public String getSocialMedia() { return socialMedia; }

    // Setters
    public void setName(String name) { this.name = name; }
    public void setUsername(String username) { this.username = username; }
    public void setEmail(String email) { this.email = email; }
    public void setBio(String bio) { this.bio = bio; }
    public void setWebsite(String website) { this.website = website; }
    public void setSocialMedia(String socialMedia) { this.socialMedia = socialMedia; }
}