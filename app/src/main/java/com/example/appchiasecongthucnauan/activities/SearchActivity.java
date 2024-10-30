package com.example.appchiasecongthucnauan.activities;

import android.content.Intent;
import android.content.SharedPreferences;
import android.os.Bundle;
import android.view.MenuItem;
import android.widget.EditText;
import android.widget.ImageButton;
import android.widget.Toast;

import androidx.annotation.NonNull;
import androidx.appcompat.app.AppCompatActivity;
import androidx.viewpager2.widget.ViewPager2;

import com.example.appchiasecongthucnauan.R;
import com.example.appchiasecongthucnauan.adapters.SearchViewPagerAdapter;
import com.example.appchiasecongthucnauan.apis.ApiService;
import com.example.appchiasecongthucnauan.custom.CustomBottomNavigationView;
import com.example.appchiasecongthucnauan.models.search.SearchResultDto;
import com.example.appchiasecongthucnauan.utils.RetrofitClient;
import com.google.android.material.navigation.NavigationBarView;
import com.google.android.material.tabs.TabLayout;
import com.google.android.material.tabs.TabLayoutMediator;

import retrofit2.Call;
import retrofit2.Callback;
import retrofit2.Response;

public class SearchActivity extends AppCompatActivity {

    private TabLayout tabLayout;
    private ViewPager2 viewPager;
    private ApiService apiService;
    private String token;

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

        apiService = RetrofitClient.getInstance().getApiService();
        SharedPreferences sharedPreferences = getSharedPreferences("MyAppPrefs", MODE_PRIVATE);
        token = sharedPreferences.getString("token", "");
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
        if (query.trim().isEmpty()) {
            Toast.makeText(SearchActivity.this, "Vui lòng nhập từ khóa tìm kiếm", Toast.LENGTH_SHORT).show();
            return;
        }

        Call<SearchResultDto> call = apiService.search("Bearer " + token, query);

        call.enqueue(new Callback<SearchResultDto>() {
            @Override
            public void onResponse(Call<SearchResultDto> call, Response<SearchResultDto> response) {

                if (response.isSuccessful() && response.body() != null) {
                    SearchViewPagerAdapter adapter = (SearchViewPagerAdapter) viewPager.getAdapter();
                    if (adapter != null) {
                        adapter.updateSearchResults(response.body());
                    }
                } else {

                    Toast.makeText(SearchActivity.this, "Không tìm thấy kết quả", Toast.LENGTH_SHORT).show();
                }
            }

            @Override
            public void onFailure(Call<SearchResultDto> call, Throwable t) {
                Toast.makeText(SearchActivity.this, "Lỗi khi tìm kiếm: " + t.getMessage(), Toast.LENGTH_SHORT).show();
            }
        });
    }

}
