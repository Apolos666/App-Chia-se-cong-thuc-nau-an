package com.example.appchiasecongthucnauan.activities;

import android.content.Context;
import android.content.Intent;
import android.content.SharedPreferences;
import android.os.Bundle;
import android.util.Log;
import android.widget.EditText;
import android.widget.ImageView;
import android.widget.TextView;
import android.widget.Toast;
import android.app.ProgressDialog;
import androidx.annotation.Nullable;
import androidx.appcompat.app.AppCompatActivity;
import androidx.recyclerview.widget.LinearLayoutManager;
import androidx.recyclerview.widget.PagerSnapHelper;
import androidx.recyclerview.widget.RecyclerView;
import okhttp3.OkHttpClient;
import retrofit2.Retrofit;
import retrofit2.converter.gson.GsonConverterFactory;
import retrofit2.http.GET;
import retrofit2.http.Path;
import retrofit2.Call;
import retrofit2.Callback;
import retrofit2.Response;

import com.example.appchiasecongthucnauan.R;
import com.example.appchiasecongthucnauan.adapters.CommentAdapter;
import com.example.appchiasecongthucnauan.adapters.MediaAdapter;
import com.example.appchiasecongthucnauan.apis.ApiService;
import com.example.appchiasecongthucnauan.models.Comment;
import com.example.appchiasecongthucnauan.models.CommentDto;
import com.example.appchiasecongthucnauan.models.CreateCommentRequest;
import com.example.appchiasecongthucnauan.models.RecipeDto;
import com.example.appchiasecongthucnauan.utils.SignalRManager;

import java.util.ArrayList;
import java.util.List;
import java.util.UUID;
import java.util.concurrent.TimeUnit;

public class RecipeDetailActivity extends AppCompatActivity {
    private ImageView backButton, likeButton;
    private TextView recipeName, chefName, likesCount, commentsCount;
    private TextView ingredientsText, instructionsText;
    private RecyclerView commentsRecyclerView;
    private EditText commentInput;
    private ImageView sendCommentButton;
    private CommentAdapter commentAdapter;
    private RecyclerView mediaRecyclerView;
    private MediaAdapter mediaAdapter;
    private SignalRManager signalRManager;
    private String recipeId;
    private String token;
    private String userName;
    private String userId;
    private ApiService apiService;

    @Override
    protected void onCreate(@Nullable Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_recipe_detail);

        initViews();
        setupRetrofit();
        setupSignalR();
        setupRecipe();
        setupComments();
    }

    private void initViews() {
        backButton = findViewById(R.id.backButton);
        likeButton = findViewById(R.id.likeButton);
        recipeName = findViewById(R.id.recipeName);
        chefName = findViewById(R.id.chefName);
        likesCount = findViewById(R.id.likesCount);
        commentsCount = findViewById(R.id.commentsCount);
        ingredientsText = findViewById(R.id.ingredientsText);
        instructionsText = findViewById(R.id.instructionsText);
        commentsRecyclerView = findViewById(R.id.commentsRecyclerView);
        commentInput = findViewById(R.id.commentInput);
        sendCommentButton = findViewById(R.id.sendCommentButton);
        mediaRecyclerView = findViewById(R.id.mediaRecyclerView);
        mediaRecyclerView.setLayoutManager(new LinearLayoutManager(this, LinearLayoutManager.HORIZONTAL, false));

        PagerSnapHelper snapHelper = new PagerSnapHelper();
        snapHelper.attachToRecyclerView(mediaRecyclerView);

        backButton.setOnClickListener(v -> finish());
        likeButton.setOnClickListener(v -> toggleLike());
        sendCommentButton.setOnClickListener(v -> sendComment());
    }

    private void setupRetrofit() {
        OkHttpClient client = new OkHttpClient.Builder()
                .connectTimeout(60, TimeUnit.SECONDS)
                .readTimeout(60, TimeUnit.SECONDS)
                .writeTimeout(60, TimeUnit.SECONDS)
                .build();

        Retrofit retrofit = new Retrofit.Builder()
                .baseUrl("http://10.0.2.2:5076/")
                .addConverterFactory(GsonConverterFactory.create())
                .client(client)
                .build();

        apiService = retrofit.create(ApiService.class);
    }

    private void setupSignalR() {
        SharedPreferences sharedPreferences = getSharedPreferences("MyAppPrefs", MODE_PRIVATE);
        token = sharedPreferences.getString("token", "");
        userName = sharedPreferences.getString("userName", "");
        userId = sharedPreferences.getString("userId", "");

        signalRManager = SignalRManager.getInstance();
        signalRManager.setBaseUrl("http://10.0.2.2:5076");
        try {
            signalRManager.initializeConnection("hub/comment", token);
            signalRManager.startConnection("hub/comment");
        } catch (Exception e) {
            Log.e("RecipeDetailActivity", "Lỗi khi kết nối SignalR: " + e.getMessage());
            Toast.makeText(this, "Không thể kết nối đến máy chủ bình luận", Toast.LENGTH_SHORT).show();
        }

        signalRManager.on("hub/comment", "ReceiveComment", (userId, userName, content) -> {
            runOnUiThread(() -> {
                // Chỉ thêm comment nếu nó không phải từ người dùng hiện tại
                if (!userId.equals(this.userId)) {
                    Comment newComment = new Comment(userName, content);
                    commentAdapter.addComment(newComment);
                    commentsRecyclerView.scrollToPosition(commentAdapter.getItemCount() - 1);
                }
            });
        });
    }

    private void setupRecipe() {
        recipeId = getIntent().getStringExtra("RECIPE_ID");
        if (recipeId != null) {
            fetchRecipeDetails(recipeId);
            signalRManager.joinGroup("hub/comment", "JoinRecipeGroup", recipeId);
        } else {
            Toast.makeText(this, "Không tìm thấy công thức", Toast.LENGTH_SHORT).show();
            finish();
        }
    }

    private void setupComments() {
        commentAdapter = new CommentAdapter(new ArrayList<>());
        commentsRecyclerView.setLayoutManager(new LinearLayoutManager(this));
        commentsRecyclerView.setAdapter(commentAdapter);
    }

    private void toggleLike() {
        // Implement like functionality
    }

    private void sendComment() {
        String commentText = commentInput.getText().toString();
        if (!commentText.isEmpty()) {
            CreateCommentRequest request = new CreateCommentRequest(UUID.fromString(recipeId), commentText);
            Call<CommentDto> call = apiService.createComment("Bearer " + token, request);
            call.enqueue(new Callback<CommentDto>() {
                @Override
                public void onResponse(Call<CommentDto> call, Response<CommentDto> response) {
                    if (response.isSuccessful() && response.body() != null) {
                        commentInput.setText("");
                        // Thêm comment vào adapter ngay lập tức cho người gửi
                        CommentDto commentDto = response.body();
                        Comment newComment = new Comment(commentDto.getUserName(), commentDto.getContent());
                        runOnUiThread(() -> {
                            commentAdapter.addComment(newComment);
                            commentsRecyclerView.scrollToPosition(commentAdapter.getItemCount() - 1);
                        });
                    } else {
                        Toast.makeText(RecipeDetailActivity.this, "Không thể gửi bình luận", Toast.LENGTH_SHORT).show();
                    }
                }

                @Override
                public void onFailure(Call<CommentDto> call, Throwable t) {
                    Toast.makeText(RecipeDetailActivity.this, "Lỗi kết nối: " + t.getMessage(), Toast.LENGTH_SHORT)
                            .show();
                }
            });
        }
    }

    private void fetchRecipeDetails(String recipeId) {
        ProgressDialog progressDialog = new ProgressDialog(this);
        progressDialog.setMessage("Đang tải công thức...");
        progressDialog.setCancelable(false);
        progressDialog.show();

        Call<RecipeDto> call = apiService.getRecipe(recipeId);
        call.enqueue(new Callback<RecipeDto>() {
            @Override
            public void onResponse(Call<RecipeDto> call, Response<RecipeDto> response) {
                progressDialog.dismiss();
                if (response.isSuccessful() && response.body() != null) {
                    updateUI(response.body());
                } else {
                    Log.e("RecipeDetailActivity", "Lỗi khi tải công thức. Mã lỗi: " + response.code());
                    Toast.makeText(RecipeDetailActivity.this, "Không thể tải công thức", Toast.LENGTH_SHORT).show();
                }
            }

            @Override
            public void onFailure(Call<RecipeDto> call, Throwable t) {
                progressDialog.dismiss();
                Log.e("RecipeDetailActivity", "Lỗi kết nối: " + t.getMessage());
                Toast.makeText(RecipeDetailActivity.this, "Lỗi kết nối: " + t.getMessage(), Toast.LENGTH_SHORT).show();
            }
        });
    }

    private void updateUI(RecipeDto recipe) {
        recipeName.setText(recipe.getTitle());
        chefName.setText(recipe.getUserName());
        likesCount.setText(String.valueOf(recipe.getLikesCount()));
        commentsCount.setText(String.valueOf(recipe.getComments().size()));
        ingredientsText.setText(recipe.getIngredients());
        instructionsText.setText(recipe.getInstructions());

        // Cập nhật hình ảnh nu có
        if (recipe.getMediaUrls() != null && !recipe.getMediaUrls().isEmpty()) {
            mediaAdapter = new MediaAdapter(this, recipe.getMediaUrls());
            mediaRecyclerView.setAdapter(mediaAdapter);
        }

        // Cập nhật danh sách bình luận
        List<Comment> comments = new ArrayList<>();
        for (CommentDto commentDto : recipe.getComments()) {
            comments.add(new Comment(commentDto.getUserName(), commentDto.getContent()));
        }
        commentAdapter.setComments(comments);
    }

    @Override
    protected void onDestroy() {
        super.onDestroy();
        if (recipeId != null) {
            signalRManager.leaveGroup("hub/comment", "LeaveRecipeGroup", recipeId);
        }
        signalRManager.stopConnection("hub/comment");
    }

    public static void start(Context context, String recipeId) {
        Intent intent = new Intent(context, RecipeDetailActivity.class);
        intent.putExtra("RECIPE_ID", recipeId);
        context.startActivity(intent);
    }
}
