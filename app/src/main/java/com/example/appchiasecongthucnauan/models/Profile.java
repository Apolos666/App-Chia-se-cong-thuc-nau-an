package com.example.appchiasecongthucnauan.models;

public class Profile {
    private String name;
    private String handle;
    private int recipesCount;
    private int followersCount;
    private int followingCount;
    private String[] gridItems;

    public Profile(String name, String handle, int recipesCount, int followersCount, int followingCount, String[] gridItems) {
        this.name = name;
        this.handle = handle;
        this.recipesCount = recipesCount;
        this.followersCount = followersCount;
        this.followingCount = followingCount;
        this.gridItems = gridItems;
    }

    // Getters
    public String getName() { return name; }
    public String getHandle() { return handle; }
    public int getRecipesCount() { return recipesCount; }
    public int getFollowersCount() { return followersCount; }
    public int getFollowingCount() { return followingCount; }
    public String[] getGridItems() { return gridItems; }
}
