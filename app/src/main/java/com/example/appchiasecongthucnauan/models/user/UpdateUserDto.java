package com.example.appchiasecongthucnauan.models;

public class UpdateUserDto {
    private String name;
    private String bio;
    private String socialMedia;

    public UpdateUserDto(String name, String bio, String socialMedia) {
        this.name = name;
        this.bio = bio;
        this.socialMedia = socialMedia;
    }

    public String getName() {
        return name;
    }

    public void setName(String name) {
        this.name = name;
    }

    public String getBio() {
        return bio;
    }

    public void setBio(String bio) {
        this.bio = bio;
    }

    public String getSocialMedia() {
        return socialMedia;
    }

    public void setSocialMedia(String socialMedia) {
        this.socialMedia = socialMedia;
    }
}
