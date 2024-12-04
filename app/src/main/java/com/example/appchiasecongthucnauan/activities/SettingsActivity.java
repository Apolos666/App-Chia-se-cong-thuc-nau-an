package com.example.appchiasecongthucnauan.activities;

import android.content.Intent;
import android.content.SharedPreferences;
import android.os.Bundle;
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
        TextView tvMessages = findViewById(R.id.tvMessages);
        TextView tvPrivacy = findViewById(R.id.tvPrivacy);
        TextView tvHelpSupport = findViewById(R.id.tvHelpSupport);
        TextView tvThanhVienNhom = findViewById(R.id.tvThanhVienNhom);
        TextView nameTextView = findViewById(R.id.nameTextView);
        TextView handleTextView = findViewById(R.id.handleTextView);

        SharedPreferences sharedPreferences = getSharedPreferences("MyAppPrefs", MODE_PRIVATE);
        String username = sharedPreferences.getString("userName", "");
        String userId = sharedPreferences.getString("userId", "");

        nameTextView.setText(username);
        handleTextView.setText("@" + username);

        btnSignOut.setOnClickListener(v -> {
            Intent intent = new Intent(SettingsActivity.this, LoginActivity.class);
            startActivity(intent);
        });

        View.OnClickListener optionClickListener = v -> {
            String option = ((TextView) v).getText().toString();
            Toast.makeText(SettingsActivity.this, option + " selected", Toast.LENGTH_SHORT).show();
        };

        tvMyProfile.setOnClickListener(view -> {
            Intent intent = new Intent(SettingsActivity.this, ProfileActivity.class);
            intent.putExtra("USER_ID", userId);
            startActivity(intent);
        });
        tvMyBookmark.setOnClickListener(view -> {
            Intent intent = new Intent(SettingsActivity.this, BookmarksActivity.class);
            startActivity(intent);
        });
        tvMessages.setOnClickListener(view -> {
            Intent intent = new Intent(SettingsActivity.this, ConversationListActivity.class);
            startActivity(intent);
        });
        tvThanhVienNhom.setOnClickListener(view -> {
            Intent intent = new Intent(SettingsActivity.this, ThanhVienNhomActivity.class);
            startActivity(intent);
        });
        tvPrivacy.setOnClickListener(view -> {
            Intent intent = new Intent(SettingsActivity.this, PrivacyPolicyActivity.class);
            startActivity(intent);
        });
        tvHelpSupport.setOnClickListener(view -> {
            Intent intent = new Intent(SettingsActivity.this, SupportActivity.class);
            startActivity(intent);
        });
    }

    private void setupNavigationView() {
        bottomNavigationView = findViewById(R.id.bottom_navigation);
        bottomNavigationView.setSelectedItemId(R.id.navigation_profile); // Set profile as selected

        bottomNavigationView.setOnItemSelectedListener(item -> {
            int itemId = item.getItemId();
            if (itemId == R.id.navigation_home) {
                startActivity(new Intent(SettingsActivity.this, HomePageActivity.class));
                finish();
                return true;
            } else if (itemId == R.id.navigation_explorer) {
                finish();
                return true;
            } else return itemId == R.id.navigation_profile;
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