package com.example.appchiasecongthucnauan.models;

import java.util.UUID;

public class CreateCommentRequest {
    private UUID recipeId;
    private String content;

    public CreateCommentRequest(UUID recipeId, String content) {
        this.recipeId = recipeId;
        this.content = content;
    }

    public UUID getRecipeId() {
        return recipeId;
    }

    public void setRecipeId(UUID recipeId) {
        this.recipeId = recipeId;
    }

    public String getContent() {
        return content;
    }

    public void setContent(String content) {
        this.content = content;
    }

}
