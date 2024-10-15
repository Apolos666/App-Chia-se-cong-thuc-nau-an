package com.example.appchiasecongthucnauan.activities;

import android.content.Intent;
import android.os.Bundle;
import android.util.Log;
import android.view.MenuItem;
import android.widget.ImageView;

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

public class HomePageActivity extends AppCompatActivity implements PostAdapter.OnPostClickListener {

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
        postAdapter = new PostAdapter(this, postList, this);
        recyclerView.setAdapter(postAdapter);

        // Initialize navigation
        initializeNavigation();

        ImageView addRecipe = findViewById(R.id.add_recipe);
        addRecipe.setOnClickListener(v -> {
//            Intent intent = new Intent(HomePageActivity.this, AddRecipeActivity.class);
        });
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
                    startActivity(new Intent(HomePageActivity.this, ExploreActivity.class));
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

    @Override
    public void onPostClick(Post post) {
        Intent intent = new Intent(this, RecipeDetailActivity.class);
        intent.putExtra("RECIPE_NAME", post.getRecipeName());
        intent.putExtra("CHEF_NAME", post.getChefName());
        intent.putExtra("LIKE_COUNT", post.getLikeCount());
        intent.putExtra("COMMENT_COUNT", post.getCommentCount());
        startActivity(intent);
    }
}

