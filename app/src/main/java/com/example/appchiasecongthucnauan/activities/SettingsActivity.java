package com.example.appchiasecongthucnauan.activities;

import android.os.Bundle;
import android.view.View;
import android.widget.Button;
import android.widget.TextView;
import android.widget.Toast;

import androidx.appcompat.app.AppCompatActivity;

import com.example.appchiasecongthucnauan.R;

public class SettingsActivity extends AppCompatActivity {

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_settings);

        Button btnSignOut = findViewById(R.id.btnSignOut);
        TextView tvMyProfile = findViewById(R.id.tvMyProfile);
        TextView tvMyBookmark = findViewById(R.id.tvMyBookmark);
        TextView tvPrivacy = findViewById(R.id.tvPrivacy);
        TextView tvHelpSupport = findViewById(R.id.tvHelpSupport);

        btnSignOut.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                // Implement sign out logic here
                Toast.makeText(SettingsActivity.this, "Signing out...", Toast.LENGTH_SHORT).show();
            }
        });

        View.OnClickListener optionClickListener = new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                String option = ((TextView) v).getText().toString();
                Toast.makeText(SettingsActivity.this, option + " selected", Toast.LENGTH_SHORT).show();
                // Implement navigation or action for each option
            }
        };

        tvMyProfile.setOnClickListener(optionClickListener);
        tvMyBookmark.setOnClickListener(optionClickListener);
        tvPrivacy.setOnClickListener(optionClickListener);
        tvHelpSupport.setOnClickListener(optionClickListener);
    }
}