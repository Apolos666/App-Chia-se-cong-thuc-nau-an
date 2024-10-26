package com.example.appchiasecongthucnauan.activities;

import android.content.Intent;
import android.content.SharedPreferences;
import android.os.Bundle;
import android.util.Log;
import android.view.View;
import android.widget.Button;
import android.widget.ImageButton;
import android.widget.TextView;
import android.widget.Toast;

import androidx.appcompat.app.AppCompatActivity;
import androidx.recyclerview.widget.GridLayoutManager;
import androidx.recyclerview.widget.LinearLayoutManager;
import androidx.recyclerview.widget.RecyclerView;

import com.example.appchiasecongthucnauan.R;
import com.example.appchiasecongthucnauan.adapters.PhotoAdapter;
import com.example.appchiasecongthucnauan.apis.ApiService;
import com.example.appchiasecongthucnauan.models.user.UserDto;
import com.example.appchiasecongthucnauan.models.user.RecipeDto;

import java.util.ArrayList;
import java.util.List;

import retrofit2.Call;
import retrofit2.Callback;
import retrofit2.Response;
import retrofit2.Retrofit;
import retrofit2.converter.gson.GsonConverterFactory;

public class ProfileActivity extends AppCompatActivity {
    private RecyclerView recyclerView;
    private PhotoAdapter adapter;
    private boolean isGridView = true;
    private boolean isOwnProfile = false;
    private ApiService apiService;
    private String token;
    private String currentUserId;

    private static final String TAG = "ProfileActivity";

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_profile);

        String userId = getIntent().getStringExtra("USER_ID");
        setupRetrofit();

        // Lấy userId của người dùng hiện tại từ SharedPreferences
        SharedPreferences sharedPreferences = getSharedPreferences("MyAppPrefs", MODE_PRIVATE);
        currentUserId = sharedPreferences.getString("userId", "");

        // Log giá trị của userId và currentUserId
        Log.d(TAG, "userId từ Intent: " + userId);
        Log.d(TAG, "currentUserId từ SharedPreferences: " + currentUserId);

        if (userId != null) {
            // Kiểm tra xem profile này có phải của người dùng hiện tại không
            isOwnProfile = userId.trim().equals(currentUserId.trim());
            Log.d(TAG, "isOwnProfile: " + isOwnProfile);

            // Gọi setupViews với isOwnProfile
            setupViews(isOwnProfile);

            loadUserProfile(userId);
        } else {
            Log.e(TAG, "userId từ Intent là null");
            Toast.makeText(this, "Không tìm thấy thông tin người dùng", Toast.LENGTH_SHORT).show();
            finish();
        }
    }

    private void setupRetrofit() {
        Retrofit retrofit = new Retrofit.Builder()
                .baseUrl("http://10.0.2.2:5076/")
                .addConverterFactory(GsonConverterFactory.create())
                .build();

        apiService = retrofit.create(ApiService.class);

        SharedPreferences sharedPreferences = getSharedPreferences("MyAppPrefs", MODE_PRIVATE);
        token = sharedPreferences.getString("token", "");
    }

    private void setupViews(boolean isOwnProfile) {
        recyclerView = findViewById(R.id.recyclerView);
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
        Button chatButton = findViewById(R.id.chatButton);

        if (isOwnProfile) {
            editProfileButton.setVisibility(View.VISIBLE);
            chatButton.setVisibility(View.GONE);
            editProfileButton.setOnClickListener(v -> {
                Intent intent = new Intent(ProfileActivity.this, EditProfileActivity.class);
                startActivity(intent);
            });
        } else {
            editProfileButton.setVisibility(View.GONE);
            chatButton.setVisibility(View.VISIBLE);
            chatButton.setOnClickListener(v -> {
                Intent intent = new Intent(ProfileActivity.this, ChatActivity.class);
                intent.putExtra("RECIPIENT_ID", getIntent().getStringExtra("USER_ID"));
                startActivity(intent);
            });
        }
    }

    private void loadUserProfile(String userId) {
        Call<UserDto> call = apiService.getUserById(userId, "Bearer " + token);
        call.enqueue(new Callback<UserDto>() {
            @Override
            public void onResponse(Call<UserDto> call, Response<UserDto> response) {
                if (response.isSuccessful() && response.body() != null) {
                    updateUI(response.body());
                } else {
                    Toast.makeText(ProfileActivity.this, "Không thể tải thông tin người dùng", Toast.LENGTH_SHORT)
                            .show();
                }
            }

            @Override
            public void onFailure(Call<UserDto> call, Throwable t) {
                Toast.makeText(ProfileActivity.this, "Lỗi kết nối: " + t.getMessage(), Toast.LENGTH_SHORT).show();
            }
        });
    }

    private void updateUI(UserDto user) {
        TextView userName = findViewById(R.id.userName);
        TextView userHandle = findViewById(R.id.userHandle);
        TextView recipesCount = findViewById(R.id.recipesCount);
        TextView followersCount = findViewById(R.id.followersCount);
        TextView followingCount = findViewById(R.id.followingCount);

        userName.setText(user.getName());
        userHandle.setText("@" + user.getName().toLowerCase().replace(" ", ""));
        recipesCount.setText(user.getRecipes().size() + "\nCông thức");
        followersCount.setText(user.getFollowers().size() + "\nNgười theo dõi");
        followingCount.setText(user.getFollowing().size() + "\nĐang theo dõi");

        List<String> mediaUrls = new ArrayList<>();
        for (RecipeDto recipe : user.getRecipes()) {
            String imageUrl = getFirstImageUrl(recipe.getMediaUrls());
            if (imageUrl != null) {
                mediaUrls.add(imageUrl);
            }
        }

        adapter = new PhotoAdapter(this, mediaUrls);
        recyclerView.setAdapter(adapter);
    }

    private String getFirstImageUrl(List<String> mediaUrls) {
        for (String url : mediaUrls) {
            if (url.toLowerCase().endsWith(".jpg") || url.toLowerCase().endsWith(".png")) {
                return url;
            }
        }
        return null;
    }

    private void setGridLayout() {
        recyclerView.setLayoutManager(new GridLayoutManager(this, 2));
        if (adapter != null) {
            adapter.setViewType(PhotoAdapter.VIEW_TYPE_GRID);
        }
    }

    private void setListLayout() {
        recyclerView.setLayoutManager(new LinearLayoutManager(this));
        if (adapter != null) {
            adapter.setViewType(PhotoAdapter.VIEW_TYPE_LIST);
        }
    }
}
