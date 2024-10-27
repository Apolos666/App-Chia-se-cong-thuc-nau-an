package com.example.appchiasecongthucnauan.activities;

import android.content.Intent;
import android.content.SharedPreferences;
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
import android.widget.Button;
import android.widget.ProgressBar;

import androidx.annotation.Nullable;
import androidx.appcompat.app.AppCompatActivity;

import com.example.appchiasecongthucnauan.R;
import com.example.appchiasecongthucnauan.apis.ApiService;
import com.example.appchiasecongthucnauan.models.UpdateUserDto;
import com.example.appchiasecongthucnauan.models.UserProfile;
import com.example.appchiasecongthucnauan.models.user.UserDto;
import com.example.appchiasecongthucnauan.utils.RetrofitClient;

import java.io.IOException;
import okhttp3.OkHttpClient;
import retrofit2.Call;
import retrofit2.Callback;
import retrofit2.Response;
import retrofit2.Retrofit;
import retrofit2.converter.gson.GsonConverterFactory;
import java.util.concurrent.TimeUnit;

public class EditProfileActivity extends AppCompatActivity {
    private ImageView profileImage;
    private EditText editName, editUsername, editEmail, editBio, editWebsite, editSocialMedia;
    private UserProfile userProfile;
    private static final int PICK_IMAGE_REQUEST = 1;
    private Uri selectedImageUri;
    private String userId;
    private String token;
    private ProgressBar progressBar;
    private Button btnSave;
    private ApiService apiService;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_edit_profile);

        setupRetrofit();
        initViews();
        setupListeners();
        setupCircularImageView();

        userId = getIntent().getStringExtra("CURRENT_USER_ID");
        SharedPreferences sharedPreferences = getSharedPreferences("MyAppPrefs", MODE_PRIVATE);
        token = sharedPreferences.getString("token", "");

        loadUserData();

        progressBar = findViewById(R.id.progressBar);
        btnSave = findViewById(R.id.btnSave);
    }

    private void setupRetrofit() {
        apiService = RetrofitClient.getInstance().getApiService();
    }

    private void initViews() {
        profileImage = findViewById(R.id.profileImage);
        editName = findViewById(R.id.editName);
        editBio = findViewById(R.id.editBio);
        editSocialMedia = findViewById(R.id.editSocialMedia);
        btnSave = findViewById(R.id.btnSave);
        progressBar = findViewById(R.id.progressBar);
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

    private void loadUserData() {
        Call<UserDto> call = apiService.getUserById(userId, "Bearer " + token);
        call.enqueue(new Callback<UserDto>() {
            @Override
            public void onResponse(Call<UserDto> call, Response<UserDto> response) {
                if (response.isSuccessful() && response.body() != null) {
                    UserDto user = response.body();
                    updateUI(user);
                } else {
                    Toast.makeText(EditProfileActivity.this, "Không thể tải thông tin người dùng", Toast.LENGTH_SHORT)
                            .show();
                }
            }

            @Override
            public void onFailure(Call<UserDto> call, Throwable t) {
                Toast.makeText(EditProfileActivity.this, "Lỗi kết nối: " + t.getMessage(), Toast.LENGTH_SHORT).show();
            }
        });
    }

    private void updateUI(UserDto user) {
        editName.setText(user.getName());
        editBio.setText(user.getBio());
        editSocialMedia.setText(user.getSocialMedia());
    }

    private void setupListeners() {
        // Back button
        findViewById(R.id.btnBack).setOnClickListener(v -> {
            onBackPressed();
        });

        // Save button
        if (btnSave != null) {
            btnSave.setOnClickListener(v -> {
                saveProfile();
            });
        }

        // Change photo button
        findViewById(R.id.btnChangePhoto).setOnClickListener(v -> {
            openImagePicker();
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
        String name = editName.getText().toString().trim();
        String bio = editBio.getText().toString().trim();
        String socialMedia = editSocialMedia.getText().toString().trim();

        UpdateUserDto updateUserDto = new UpdateUserDto(name, bio, socialMedia);

        // Hiển thị ProgressBar và vô hiệu hóa nút Save
        progressBar.setVisibility(View.VISIBLE);
        btnSave.setEnabled(false);

        Call<Void> call = apiService.updateUser(userId, "Bearer " + token, updateUserDto);
        call.enqueue(new Callback<Void>() {
            @Override
            public void onResponse(Call<Void> call, Response<Void> response) {
                // Ẩn ProgressBar và kích hoạt lại nút Save
                progressBar.setVisibility(View.GONE);
                btnSave.setEnabled(true);

                if (response.isSuccessful()) {
                    Toast.makeText(EditProfileActivity.this, "Cập nhật thông tin thành công", Toast.LENGTH_SHORT)
                            .show();
                    finish();
                } else {
                    Toast.makeText(EditProfileActivity.this, "Không thể cập nhật thông tin", Toast.LENGTH_SHORT).show();
                }
            }

            @Override
            public void onFailure(Call<Void> call, Throwable t) {
                // Ẩn ProgressBar và kích hoạt lại nút Save
                progressBar.setVisibility(View.GONE);
                btnSave.setEnabled(true);

                Toast.makeText(EditProfileActivity.this, "Lỗi kết nối: " + t.getMessage(), Toast.LENGTH_SHORT).show();
            }
        });
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
