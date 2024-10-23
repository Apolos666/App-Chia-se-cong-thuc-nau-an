package com.example.appchiasecongthucnauan.models;

/**
 * A recipe post on homepage
 */
public class Post {
    private String recipeName;
    private String chefName;
    private int likeCount;
    private int commentCount;
    private String mediaUrl;

    public Post(String recipeName, String chefName, int likeCount, int commentCount, String mediaUrl) {
        this.recipeName = recipeName;
        this.chefName = chefName;
        this.likeCount = likeCount;
        this.commentCount = commentCount;
        this.mediaUrl = mediaUrl;
    }

    public String getRecipeName() {
        return recipeName;
    }

    public String getChefName() {
        return chefName;
    }

    public int getLikeCount() {
        return likeCount;
    }

    public int getCommentCount() {
        return commentCount;
    }

    public String getMediaUrl() {
        return mediaUrl;
    }
}
