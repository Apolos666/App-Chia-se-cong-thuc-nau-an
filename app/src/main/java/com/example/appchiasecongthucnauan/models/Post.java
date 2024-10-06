package com.example.appchiasecongthucnauan.models;

/**
 * A recipe post on homepage
 */
public class Post {
    private String recipeName;
    private String chefName;
    private int likeCount;
    private int commentCount;

    public Post(String recipeName, String chefName, int likeCount, int commentCount) {
        this.recipeName = recipeName;
        this.chefName = chefName;
        this.likeCount = likeCount;
        this.commentCount = commentCount;
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
}

