package com.example.appchiasecongthucnauan.activities;

import android.content.Intent;
import android.os.Bundle;
import android.widget.Button;
import android.widget.ImageButton;
import android.widget.TextView;

import androidx.appcompat.app.AppCompatActivity;
import androidx.recyclerview.widget.GridLayoutManager;
import androidx.recyclerview.widget.LinearLayoutManager;
import androidx.recyclerview.widget.RecyclerView;

import com.example.appchiasecongthucnauan.R;
import com.example.appchiasecongthucnauan.adapters.PhotoAdapter;
import com.example.appchiasecongthucnauan.models.Profile;

import java.util.Arrays;

public class ProfileActivity extends AppCompatActivity {
    private Profile profile;
    private RecyclerView recyclerView;
    private PhotoAdapter adapter;
    private boolean isGridView = true;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_profile);

        profile = createMockProfile();

        // Set up profile info
        TextView userName = findViewById(R.id.userName);
        TextView userHandle = findViewById(R.id.userHandle);
        TextView recipesCount = findViewById(R.id.recipesCount);
        TextView followersCount = findViewById(R.id.followersCount);
        TextView followingCount = findViewById(R.id.followingCount);

        userName.setText(profile.getName());
        userHandle.setText("@" + profile.getHandle());
        recipesCount.setText(profile.getRecipesCount() + "\nRecipes");
        followersCount.setText(profile.getFollowersCount() + "\nFollowers");
        followingCount.setText(profile.getFollowingCount() + "\nFollowing");

        recyclerView = findViewById(R.id.recyclerView);
        adapter = new PhotoAdapter(this, Arrays.asList(profile.getGridItems()));
        recyclerView.setAdapter(adapter);
        setGridLayout();

        ImageButton btnGrid = findViewById(R.id.btnGrid);
        ImageButton btnList = findViewById(R.id.btnList);

        btnGrid.setOnClickListener(v -> {
            if (!isGridView) {
                setGridLayout();
                isGridView = true;
            }
        });

        btnList.setOnClickListener(v -> {
            if (isGridView) {
                setListLayout();
                isGridView = false;
            }
        });

        Button editProfileButton = findViewById(R.id.editProfileButton);
        editProfileButton.setOnClickListener(v -> {
            Intent intent = new Intent(ProfileActivity.this, EditProfileActivity.class);
            startActivity(intent);
        });
    }

    private void setGridLayout() {
        recyclerView.setLayoutManager(new GridLayoutManager(this, 2));
        adapter.setViewType(PhotoAdapter.VIEW_TYPE_GRID);
    }

    private void setListLayout() {
        recyclerView.setLayoutManager(new LinearLayoutManager(this));
        adapter.setViewType(PhotoAdapter.VIEW_TYPE_LIST);
    }

    private Profile createMockProfile() {
        String[] gridItems = {
                "https://example.com/recipe1.jpg",
                "https://example.com/recipe2.jpg",
                "https://example.com/recipe3.jpg",
                "https://example.com/recipe4.jpg",
                "https://example.com/recipe5.jpg",
                "https://example.com/recipe6.jpg",
                "https://example.com/recipe7.jpg",
                "https://example.com/recipe8.jpg",
        };
        return new Profile("Jane Doe", "janedoe", 250, 15200, 1200, gridItems);
    }
}
