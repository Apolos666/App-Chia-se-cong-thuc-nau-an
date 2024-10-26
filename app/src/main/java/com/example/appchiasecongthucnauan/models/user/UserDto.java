package com.example.appchiasecongthucnauan.models.user;

import java.util.List;
import java.util.UUID;

public class UserDto {
    private UUID id;
    private String email;
    private String name;
    private String bio;
    private String socialMedia;
    private List<RecipeDto> recipes;
    private List<UserFollowDto> following;
    private List<UserFollowDto> followers;

    // Getters and setters
    public UUID getId() {
        return id;
    }

    public void setId(UUID id) {
        this.id = id;
    }

    public String getEmail() {
        return email;
    }

    public void setEmail(String email) {
        this.email = email;
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

    public List<RecipeDto> getRecipes() {
        return recipes;
    }

    public void setRecipes(List<RecipeDto> recipes) {
        this.recipes = recipes;
    }

    public List<UserFollowDto> getFollowing() {
        return following;
    }

    public void setFollowing(List<UserFollowDto> following) {
        this.following = following;
    }

    public List<UserFollowDto> getFollowers() {
        return followers;
    }

    public void setFollowers(List<UserFollowDto> followers) {
        this.followers = followers;
    }
}

