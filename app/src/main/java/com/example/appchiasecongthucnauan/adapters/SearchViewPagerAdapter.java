package com.example.appchiasecongthucnauan.adapters;

import androidx.annotation.NonNull;
import androidx.fragment.app.Fragment;
import androidx.fragment.app.FragmentActivity;
import androidx.viewpager2.adapter.FragmentStateAdapter;

import com.example.appchiasecongthucnauan.fragments.AllFragment;
import com.example.appchiasecongthucnauan.fragments.UsersFragment;
import com.example.appchiasecongthucnauan.fragments.RecipesFragment;

public class SearchViewPagerAdapter extends FragmentStateAdapter {
    private String currentQuery = "";

    public SearchViewPagerAdapter(@NonNull FragmentActivity fragmentActivity) {
        super(fragmentActivity);
    }

    @NonNull
    @Override
    public Fragment createFragment(int position) {
        switch (position) {
            case 0:
                return new AllFragment();
            case 1:
                return new UsersFragment();
            case 2:
                return new RecipesFragment();
            default:
                return new AllFragment();
        }
    }

    @Override
    public int getItemCount() {
        return 3;
    }

    public void updateSearchResults(String query) {
        this.currentQuery = query;
        // Notify fragments to update their content
        // You need to implement a method to notify the fragments
        // This could be done through a shared ViewModel or by calling a method on each fragment
    }
}