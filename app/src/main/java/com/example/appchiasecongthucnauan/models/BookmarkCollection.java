package com.example.appchiasecongthucnauan.models;

public class BookmarkCollection {
    private String name;
    private int recipeCount;

    public BookmarkCollection(String name, int recipeCount) {
        this.name = name;
        this.recipeCount = recipeCount;
    }

    public String getName() {
        return name;
    }

    public int getRecipeCount() {
        return recipeCount;
    }
}

