package com.example.appchiasecongthucnauan.models.search;

import com.example.appchiasecongthucnauan.models.search.UserSearchResultDto;

import java.util.List;

public class SearchResultDto {
    private List<UserSearchResultDto> users;
    private List<RecipeSearchResultDto> recipes;

    // Getters and setters
    public List<UserSearchResultDto> getUsers() { return users; }
    public void setUsers(List<UserSearchResultDto> users) { this.users = users; }
    public List<RecipeSearchResultDto> getRecipes() { return recipes; }
    public void setRecipes(List<RecipeSearchResultDto> recipes) { this.recipes = recipes; }
} 