package com.example.appchiasecongthucnauan.models;

public class Recipe {
    private String name;
    private String chef;
    private int likes;
    private int comments;

    public Recipe(String name, String chef, int likes, int comments) {
        this.name = name;
        this.chef = chef;
        this.likes = likes;
        this.comments = comments;
    }

    public String getName() {
        return name;
    }

    public void setName(String name) {
        this.name = name;
    }

    public String getChef() {
        return chef;
    }

    public void setChef(String chef) {
        this.chef = chef;
    }

    public int getLikes() {
        return likes;
    }

    public void setLikes(int likes) {
        this.likes = likes;
    }

    public int getComments() {
        return comments;
    }

    public void setComments(int comments) {
        this.comments = comments;
    }
}