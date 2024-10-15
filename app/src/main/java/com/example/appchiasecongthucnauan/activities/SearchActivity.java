package com.example.appchiasecongthucnauan.activities;

import android.content.Intent;
import android.os.Bundle;
import android.view.MenuItem;
import android.widget.EditText;
import android.widget.ImageButton;

import androidx.annotation.NonNull;
import androidx.appcompat.app.AppCompatActivity;
import androidx.viewpager2.widget.ViewPager2;

import com.example.appchiasecongthucnauan.R;
import com.example.appchiasecongthucnauan.adapters.SearchViewPagerAdapter;
import com.example.appchiasecongthucnauan.custom.CustomBottomNavigationView;
import com.google.android.material.navigation.NavigationBarView;
import com.google.android.material.tabs.TabLayout;
import com.google.android.material.tabs.TabLayoutMediator;

public class SearchActivity extends AppCompatActivity {

    private TabLayout tabLayout;
    private ViewPager2 viewPager;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_search);

        tabLayout = findViewById(R.id.tabLayout_1);
        viewPager = findViewById(R.id.viewPager_1);

        setupViewPager();
        setupTabLayout();
        initializeNavigation();
        setupSearchFunctionality();
    }

    private void setupViewPager() {
        SearchViewPagerAdapter adapter = new SearchViewPagerAdapter(this);
        viewPager.setAdapter(adapter);
    }

    private void setupTabLayout() {
        new TabLayoutMediator(tabLayout, viewPager, (tab, position) -> {
            switch (position) {
                case 0:
                    tab.setText("Tất cả");
                    break;
                case 1:
                    tab.setText("Người dùng");
                    break;
                case 2:
                    tab.setText("Công thức");
                    break;
            }
        }).attach();
    }

    private void initializeNavigation() {
        CustomBottomNavigationView bottomNavigationView = findViewById(R.id.bottom_navigation);

        bottomNavigationView.setOnItemSelectedListener(new NavigationBarView.OnItemSelectedListener() {
            @Override
            public boolean onNavigationItemSelected(@NonNull MenuItem item) {
                int itemId = item.getItemId();
                if (itemId == R.id.navigation_home) {
                    startActivity(new Intent(SearchActivity.this, HomePageActivity.class));
                    return true;
                } else if (itemId == R.id.navigation_explorer) {
                    startActivity(new Intent(SearchActivity.this, ExploreActivity.class));
                    return true;
                } else if (itemId == R.id.navigation_profile) {
                    startActivity(new Intent(SearchActivity.this, SettingsActivity.class));
                    return true;
                }
                return false;
            }
        });
    }

    private void setupSearchFunctionality() {
        EditText searchEditText = findViewById(R.id.searchEditText);
        ImageButton searchButton = findViewById(R.id.searchButton);
        ImageButton backButton = findViewById(R.id.backButton);

        searchButton.setOnClickListener(v -> performSearch(searchEditText.getText().toString()));
        backButton.setOnClickListener(v -> onBackPressed());
    }

    private void performSearch(String query) {
        SearchViewPagerAdapter adapter = (SearchViewPagerAdapter) viewPager.getAdapter();
        if (adapter != null) {
            adapter.updateSearchResults(query);
        }
    }
}
