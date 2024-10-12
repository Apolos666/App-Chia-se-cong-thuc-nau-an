package com.example.appchiasecongthucnauan.activities;

import android.content.Intent;
import android.os.Bundle;
import android.util.Log;
import android.view.MenuItem;
import android.view.View;
import android.widget.ImageView;
import android.widget.Toast;

import androidx.annotation.NonNull;
import androidx.appcompat.app.AppCompatActivity;
import androidx.recyclerview.widget.LinearLayoutManager;
import androidx.recyclerview.widget.RecyclerView;

import com.example.appchiasecongthucnauan.R;
import com.example.appchiasecongthucnauan.adapters.PostAdapter;
import com.example.appchiasecongthucnauan.custom.CustomBottomNavigationView;
import com.example.appchiasecongthucnauan.models.Post;
import com.google.android.material.navigation.NavigationBarView;

import java.util.ArrayList;
import java.util.List;

public class HomePageActivity extends AppCompatActivity {

    private RecyclerView recyclerView;
    private PostAdapter postAdapter;
    private List<Post> postList;
    private CustomBottomNavigationView bottomNavigationView;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_home);

        // Initialize RecyclerView
        recyclerView = findViewById(R.id.recycler_view);
        recyclerView.setLayoutManager(new LinearLayoutManager(this));

        // Initialize post list and add sample data
        postList = new ArrayList<>();
        postList.add(new Post("Pasta Carbonara", "Chef John", 42, 12));
        postList.add(new Post("Grilled Salmon", "Chef Anna", 67, 18));
        postList.add(new Post("Beef Stroganoff", "Chef Mike", 120, 25));

        // Initialize the adapter and set it to the RecyclerView
        postAdapter = new PostAdapter(this, postList);
        recyclerView.setAdapter(postAdapter);

        // Initialize navigation
        initializeNavigation();
    }

    private void initializeNavigation() {
        bottomNavigationView = findViewById(R.id.bottom_navigation);

        bottomNavigationView.setOnItemSelectedListener(new NavigationBarView.OnItemSelectedListener() {
            @Override
            public boolean onNavigationItemSelected(@NonNull MenuItem item) {
                Log.d("HomePageActivity", "Navigation item clicked: " + item.getTitle());

                int itemId = item.getItemId();
                if (itemId == R.id.navigation_home) {
                    return true;
                } else if (itemId == R.id.navigation_explorer) {
                    return true;
                } else if (itemId == R.id.navigation_profile) {
                    startActivity(new Intent(HomePageActivity.this, SettingsActivity.class));
                    return true;
                }
                return false;
            }
        });
    }

    @Override
    protected void onResume() {
        super.onResume();
        if (bottomNavigationView != null) {
            bottomNavigationView.setSelectedItemId(R.id.navigation_home);
        }
    }
}

