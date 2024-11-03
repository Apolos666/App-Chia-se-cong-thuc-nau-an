package com.example.appchiasecongthucnauan.adapters;

import androidx.annotation.NonNull;
import androidx.fragment.app.Fragment;
import androidx.fragment.app.FragmentActivity;
import androidx.viewpager2.adapter.FragmentStateAdapter;

import com.example.appchiasecongthucnauan.fragments.AllFragment;
import com.example.appchiasecongthucnauan.fragments.SearchableFragment;
import com.example.appchiasecongthucnauan.fragments.UsersFragment;
import com.example.appchiasecongthucnauan.fragments.RecipesFragment;
import com.example.appchiasecongthucnauan.models.search.SearchResultDto;

import java.util.ArrayList;
import java.util.List;

public class SearchViewPagerAdapter extends FragmentStateAdapter {
    private SearchResultDto searchResults;
    private List<Fragment> fragments;

    public SearchViewPagerAdapter(@NonNull FragmentActivity fragmentActivity) {
        super(fragmentActivity);
        fragments = new ArrayList<>();
        fragments.add(new AllFragment());
        fragments.add(new UsersFragment());
        fragments.add(new RecipesFragment());
    }

    @NonNull
    @Override
    public Fragment createFragment(int position) {
        return fragments.get(position);
    }

    public void updateSearchResults(SearchResultDto results) {
        this.searchResults = results;
        for (Fragment fragment : fragments) {
            if (fragment instanceof SearchableFragment) {
                ((SearchableFragment) fragment).onSearchResultsUpdated(results);
            }
        }
    }

    @Override
    public int getItemCount() {
        return fragments.size();
    }
}