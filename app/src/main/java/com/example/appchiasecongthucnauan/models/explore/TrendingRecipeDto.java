package com.example.appchiasecongthucnauan.models.explore;

public class TrendingRecipeDto {
    private String id;
    private String title;
    private String chefName;
    private int likesCount;
    private String imageUrl;
    private boolean isTrending;

    // Getters
    public String getId() {
        return id;
    }

    public String getTitle() {
        return title;
    }

    public String getChefName() {
        return chefName;
    }

    public int getLikesCount() {
        return likesCount;
    }

    public String getImageUrl() {
        return imageUrl;
    }

    public boolean isTrending() {
        return isTrending;
    }

    // Setters
    public void setId(String id) {
        this.id = id;
    }

    public void setTitle(String title) {
        this.title = title;
    }

    public void setChefName(String chefName) {
        this.chefName = chefName;
    }

    public void setLikesCount(int likesCount) {
        this.likesCount = likesCount;
    }

    public void setImageUrl(String imageUrl) {
        this.imageUrl = imageUrl;
    }

    public void setTrending(boolean trending) {
        isTrending = trending;
    }
}