package com.example.appchiasecongthucnauan.activities;

import android.content.Intent;
import android.graphics.Bitmap;
import android.graphics.Outline;
import android.net.Uri;
import android.os.Bundle;
import android.provider.MediaStore;
import android.text.Editable;
import android.text.TextWatcher;
import android.util.Patterns;
import android.view.View;
import android.view.ViewOutlineProvider;
import android.widget.EditText;
import android.widget.ImageView;
import android.widget.Toast;

import androidx.annotation.Nullable;
import androidx.appcompat.app.AppCompatActivity;

import com.example.appchiasecongthucnauan.R;
import com.example.appchiasecongthucnauan.models.UserProfile;

import java.io.IOException;

public class EditProfileActivity extends AppCompatActivity {
    private ImageView profileImage;
    private EditText editName, editUsername, editEmail, editBio, editWebsite, editSocialMedia;
    private UserProfile userProfile;
    private static final int PICK_IMAGE_REQUEST = 1;
    private Uri selectedImageUri;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_edit_profile);

        initViews();
        loadMockData();
        setupListeners();
        setupCircularImageView();
    }

    private void initViews() {
        profileImage = findViewById(R.id.profileImage);
        editName = findViewById(R.id.editName);
        editUsername = findViewById(R.id.editUsername);
        editEmail = findViewById(R.id.editEmail);
        editBio = findViewById(R.id.editBio);
        editWebsite = findViewById(R.id.editWebsite);
        editSocialMedia = findViewById(R.id.editSocialMedia);
    }

    private void setupCircularImageView() {
        profileImage.setOutlineProvider(new ViewOutlineProvider() {
            @Override
            public void getOutline(View view, Outline outline) {
                outline.setOval(0, 0, view.getWidth(), view.getHeight());
            }
        });
        profileImage.setClipToOutline(true);
    }

    private void loadMockData() {
        userProfile = new UserProfile(
                "Jane Doe",
                "janedoe",
                "jane@exam.com",
                "Food enthusiast and amateur chef. I love creating and sharing delicious recipes!",
                "https://janedoe.com",
                "jane_doe_cooks"
        );

        // Set data to views
        editName.setText(userProfile.getName());
        editUsername.setText(userProfile.getUsername());
        editEmail.setText(userProfile.getEmail());
        editBio.setText(userProfile.getBio());
        editWebsite.setText(userProfile.getWebsite());
        editSocialMedia.setText(userProfile.getSocialMedia());

        // Set default profile image
        profileImage.setImageResource(R.drawable.default_profile);
    }

    private void setupListeners() {
        // Back button
        findViewById(R.id.btnBack).setOnClickListener(v -> {
            onBackPressed();
        });

        // Save button
        findViewById(R.id.btnSave).setOnClickListener(v -> {
            saveProfile();
        });

        // Change photo button
        findViewById(R.id.btnChangePhoto).setOnClickListener(v -> {
            openImagePicker();
        });

        // Add text change listeners for validation
        editEmail.addTextChangedListener(new TextWatcher() {
            @Override
            public void beforeTextChanged(CharSequence s, int start, int count, int after) {}

            @Override
            public void onTextChanged(CharSequence s, int start, int before, int count) {}

            @Override
            public void afterTextChanged(Editable s) {
                validateEmail();
            }
        });
    }

    private void openImagePicker() {
        Intent intent = new Intent();
        intent.setType("image/*");
        intent.setAction(Intent.ACTION_GET_CONTENT);
        startActivityForResult(Intent.createChooser(intent, "Select Picture"), PICK_IMAGE_REQUEST);
    }

    @Override
    protected void onActivityResult(int requestCode, int resultCode, @Nullable Intent data) {
        super.onActivityResult(requestCode, resultCode, data);

        if (requestCode == PICK_IMAGE_REQUEST && resultCode == RESULT_OK && data != null && data.getData() != null) {
            selectedImageUri = data.getData();
            try {
                Bitmap bitmap = MediaStore.Images.Media.getBitmap(getContentResolver(), selectedImageUri);
                profileImage.setImageBitmap(bitmap);
            } catch (IOException e) {
                e.printStackTrace();
                Toast.makeText(this, "Failed to load image", Toast.LENGTH_SHORT).show();
            }
        }
    }

    private void saveProfile() {
        if (!validateInputs()) {
            Toast.makeText(this, "Please fix the errors before saving", Toast.LENGTH_SHORT).show();
            return;
        }

        // Update userProfile object
        userProfile.setName(editName.getText().toString().trim());
        userProfile.setUsername(editUsername.getText().toString().trim());
        userProfile.setEmail(editEmail.getText().toString().trim());
        userProfile.setBio(editBio.getText().toString().trim());
        userProfile.setWebsite(editWebsite.getText().toString().trim());
        userProfile.setSocialMedia(editSocialMedia.getText().toString().trim());

        // Here you would typically save to database/backend

        // Show success message
        Toast.makeText(this, "Profile saved successfully", Toast.LENGTH_SHORT).show();

        // Close activity
        finish();
    }

    private boolean validateInputs() {
        boolean isValid = true;

        // Validate name
        if (editName.getText().toString().trim().isEmpty()) {
            editName.setError("Name is required");
            isValid = false;
        }

        // Validate username
        if (editUsername.getText().toString().trim().isEmpty()) {
            editUsername.setError("Username is required");
            isValid = false;
        }

        // Validate email
        if (!validateEmail()) {
            isValid = false;
        }

        return isValid;
    }

    private boolean validateEmail() {
        String email = editEmail.getText().toString().trim();
        if (email.isEmpty()) {
            editEmail.setError("Email is required");
            return false;
        }

        if (!Patterns.EMAIL_ADDRESS.matcher(email).matches()) {
            editEmail.setError("Please enter a valid email address");
            return false;
        }

        return true;
    }

    // Helper method to convert dp to pixels
    private int dpToPx(int dp) {
        float density = getResources().getDisplayMetrics().density;
        return Math.round(dp * density);
    }

    @Override
    public void onBackPressed() {
        // You might want to show a confirmation dialog if there are unsaved changes
        super.onBackPressed();
    }
}
