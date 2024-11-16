package com.example.appchiasecongthucnauan.models;

import com.google.gson.annotations.SerializedName;

public class BookmarkResponse {
    @SerializedName("isBookmarked")
    private boolean isBookmarked;
    
    public boolean isBookmarked() {
        return isBookmarked;
    }

    public void setBookmarked(boolean bookmarked) {
        isBookmarked = bookmarked;
    }
} 