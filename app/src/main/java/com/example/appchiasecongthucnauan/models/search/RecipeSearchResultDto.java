package com.example.appchiasecongthucnauan.models.search;

public class RecipeSearchResultDto {
    private String id;
    private String title;
    private String chefName;
    private String thumbnailUrl;

    // Getters and setters
    public String getId() { return id; }
    public void setId(String id) { this.id = id; }
    public String getTitle() { return title; }
    public void setTitle(String title) { this.title = title; }
    public String getChefName() { return chefName; }
    public void setChefName(String chefName) { this.chefName = chefName; }
    public String getThumbnailUrl() { return thumbnailUrl; }
    public void setThumbnailUrl(String thumbnailUrl) { this.thumbnailUrl = thumbnailUrl; }
} 