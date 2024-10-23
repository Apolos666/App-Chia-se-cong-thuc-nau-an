package com.example.appchiasecongthucnauan.models;

import java.util.UUID;

public class CreateRecipeDto {
    private String title;
    private String ingredients;
    private String instructions;
    private UUID recipeCategoryId;

    public String getTitle() {
        return title;
    }

    public void setTitle(String title) {
        this.title = title;
    }

    public String getIngredients() {
        return ingredients;
    }

    public void setIngredients(String ingredients) {
        this.ingredients = ingredients;
    }

    public String getInstructions() {
        return instructions;
    }

    public void setInstructions(String instructions) {
        this.instructions = instructions;
    }

    public UUID getRecipeCategoryId() {
        return recipeCategoryId;
    }

    public void setRecipeCategoryId(UUID recipeCategoryId) {
        this.recipeCategoryId = recipeCategoryId;
    }
}
