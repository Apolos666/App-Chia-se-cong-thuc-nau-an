package com.example.appchiasecongthucnauan.activities;

import android.content.Intent;
import android.os.Bundle;
import android.util.Log;
import android.view.MenuItem;
import android.view.View;
import android.widget.Button;
import android.widget.TextView;
import android.widget.Toast;

import androidx.annotation.NonNull;
import androidx.appcompat.app.AppCompatActivity;

import com.example.appchiasecongthucnauan.R;
import com.example.appchiasecongthucnauan.custom.CustomBottomNavigationView;
import com.google.android.material.navigation.NavigationBarView;

public class SettingsActivity extends AppCompatActivity {
    private CustomBottomNavigationView bottomNavigationView;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_settings);
        initializeViews();
        setupNavigationView();
    }

    private void initializeViews() {
        Button btnSignOut = findViewById(R.id.btnSignOut);
        TextView tvMyProfile = findViewById(R.id.tvMyProfile);
        TextView tvMyBookmark = findViewById(R.id.tvMyBookmark);
        TextView tvPrivacy = findViewById(R.id.tvPrivacy);
        TextView tvHelpSupport = findViewById(R.id.tvHelpSupport);

        btnSignOut.setOnClickListener(v -> {
            Toast.makeText(SettingsActivity.this, "Signing out...", Toast.LENGTH_SHORT).show();
        });

        View.OnClickListener optionClickListener = v -> {
            String option = ((TextView) v).getText().toString();
            Toast.makeText(SettingsActivity.this, option + " selected", Toast.LENGTH_SHORT).show();
        };

        tvMyProfile.setOnClickListener(optionClickListener);
        tvMyBookmark.setOnClickListener(optionClickListener);
        tvPrivacy.setOnClickListener(optionClickListener);
        tvHelpSupport.setOnClickListener(optionClickListener);
    }

    private void setupNavigationView() {
        bottomNavigationView = findViewById(R.id.bottom_navigation);
        bottomNavigationView.setSelectedItemId(R.id.navigation_profile); // Set profile as selected

        bottomNavigationView.setOnItemSelectedListener(new NavigationBarView.OnItemSelectedListener() {
            @Override
            public boolean onNavigationItemSelected(@NonNull MenuItem item) {
                int itemId = item.getItemId();
                if (itemId == R.id.navigation_home) {
                    startActivity(new Intent(SettingsActivity.this, HomePageActivity.class));
                    finish();
                    return true;
                } else if (itemId == R.id.navigation_explorer) {
                    finish();
                    return true;
                } else return itemId == R.id.navigation_profile;
            }
        });
    }

    @Override
    protected void onResume() {
        super.onResume();
        if (bottomNavigationView != null) {
            bottomNavigationView.setSelectedItemId(R.id.navigation_profile);
        }
    }
}