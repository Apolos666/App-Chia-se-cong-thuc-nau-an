package com.example.appchiasecongthucnauan.adapters;

import androidx.annotation.NonNull;
import androidx.fragment.app.Fragment;
import androidx.fragment.app.FragmentActivity;
import androidx.viewpager2.adapter.FragmentStateAdapter;

import com.example.appchiasecongthucnauan.fragments.RecentFragment;
import com.example.appchiasecongthucnauan.fragments.CategoriesFragment;
import com.example.appchiasecongthucnauan.fragments.TrendingFragment;

public class ExploreViewPagerAdapter extends FragmentStateAdapter {

    public ExploreViewPagerAdapter(FragmentActivity fa) {
        super(fa);
    }

    @NonNull
    @Override
    public Fragment createFragment(int position) {
        switch (position) {
            case 0:
                return new TrendingFragment();
            case 1:
                return new RecentFragment();
            case 2:
                return new CategoriesFragment();
            default:
                return new TrendingFragment();
        }
    }

    @Override
    public int getItemCount() {
        return 3; // Tổng số tab
    }
}
