package com.example.appchiasecongthucnauan.fragments;

import com.example.appchiasecongthucnauan.models.search.SearchResultDto;

public interface SearchableFragment {
    void onSearchResultsUpdated(SearchResultDto results);
} 