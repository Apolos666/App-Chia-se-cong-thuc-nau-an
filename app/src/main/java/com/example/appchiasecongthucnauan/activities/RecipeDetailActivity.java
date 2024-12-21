package com.example.appchiasecongthucnauan.activities;

import android.app.ProgressDialog;
import android.content.Context;
import android.content.Intent;
import android.content.SharedPreferences;
import android.os.Bundle;
import android.util.Log;
import android.view.View;
import android.widget.EditText;
import android.widget.ImageView;
import android.widget.TextView;
import android.widget.Toast;

import androidx.annotation.Nullable;
import androidx.appcompat.app.AppCompatActivity;
import androidx.appcompat.app.AlertDialog;
import androidx.recyclerview.widget.LinearLayoutManager;
import androidx.recyclerview.widget.PagerSnapHelper;
import androidx.recyclerview.widget.RecyclerView;

import com.example.appchiasecongthucnauan.R;
import com.example.appchiasecongthucnauan.adapters.CommentAdapter;
import com.example.appchiasecongthucnauan.adapters.MediaAdapter;
import com.example.appchiasecongthucnauan.apis.ApiService;
import com.example.appchiasecongthucnauan.models.BookmarkResponse;
import com.example.appchiasecongthucnauan.models.Comment;
import com.example.appchiasecongthucnauan.models.CommentDto;
import com.example.appchiasecongthucnauan.models.CreateCommentRequest;
import com.example.appchiasecongthucnauan.models.RecipeDto;
import com.example.appchiasecongthucnauan.utils.RetrofitClient;
import com.example.appchiasecongthucnauan.utils.SignalRManager;

import java.io.EOFException;
import java.io.IOException;
import java.util.ArrayList;
import java.util.List;
import java.util.UUID;

import retrofit2.Call;
import retrofit2.Callback;
import retrofit2.Response;

public class RecipeDetailActivity extends AppCompatActivity implements CommentAdapter.OnCommentAvatarClickListener {
    private ImageView backButton, bookmarkButton, likeButton;
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
    private boolean isBookmarked = false;
    private ImageView likeIconView;
    private ImageView editButton, deleteButton;
    private String currentUserId;
    private boolean isOwnRecipe = false;


    @Override
    protected void onCreate(@Nullable Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_recipe_detail);

        initViews();
        setupRetrofit();
        setupSignalR();
        setupRecipe();
        setupComments();
        checkBookmarkStatus();

    }


    private void initViews() {
        backButton = findViewById(R.id.backButton);
//        likeButton = findViewById(R.id.likeButton);
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


        sendCommentButton.setOnClickListener(v -> sendComment());
        bookmarkButton = findViewById(R.id.BookMarkButton);
        recipeId = getIntent().getStringExtra("RECIPE_ID");
        bookmarkButton.setOnClickListener(v -> toggleBookmark());
        backButton.setOnClickListener(v -> finish());
        sendCommentButton.setOnClickListener(v -> sendComment());

        likeIconView = findViewById(R.id.likeIconView);
        likeIconView.setOnClickListener(v -> toggleLike());

        editButton = findViewById(R.id.EditButton);
        deleteButton = findViewById(R.id.DeleteButton);

        SharedPreferences sharedPreferences = getSharedPreferences("MyAppPrefs", MODE_PRIVATE);
        currentUserId = sharedPreferences.getString("userId", "");

        editButton.setVisibility(View.GONE);
        deleteButton.setVisibility(View.GONE);

        editButton.setOnClickListener(v -> editRecipe());
        deleteButton.setOnClickListener(v -> deleteRecipe());
    }

    private void setupRetrofit() {
        apiService = RetrofitClient.getInstance().getApiService();
    }

    private void setupSignalR() {
        SharedPreferences sharedPreferences = getSharedPreferences("MyAppPrefs", MODE_PRIVATE);
        token = sharedPreferences.getString("token", "");
        userName = sharedPreferences.getString("userName", "");
        userId = sharedPreferences.getString("userId", "");

        signalRManager = SignalRManager.getInstance();
        try {
            signalRManager.initializeConnection("hub/comment", token);
            signalRManager.startConnection("hub/comment");

            signalRManager.on("hub/comment", "ReceiveComment", (userId, userName, content) -> {
                runOnUiThread(() -> {
                    // Chỉ thêm comment nếu nó không phải từ người dùng hiện tại
                    if (!userId.equals(this.userId)) {
                        Comment newComment = new Comment(userId, userName, content);
                        commentAdapter.addComment(newComment);
                        commentsRecyclerView.scrollToPosition(commentAdapter.getItemCount() - 1);
                    }
                });
            });
        } catch (Exception e) {
            Log.e("RecipeDetailActivity", "Lỗi khi kết nối SignalR: " + e.getMessage());
            Toast.makeText(this, "Không thể kết nối đến máy chủ bình luận", Toast.LENGTH_SHORT).show();
        }
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
        commentAdapter = new CommentAdapter(new ArrayList<>(), this);
        commentsRecyclerView.setLayoutManager(new LinearLayoutManager(this));
        commentsRecyclerView.setAdapter(commentAdapter);
    }

    private void toggleLike() {
        if (recipeId == null)
            return;

        Call<Void> call;
        if (likeIconView.isSelected()) {
            call = apiService.unlikeRecipe(recipeId, "Bearer " + token);
        } else {
            call = apiService.likeRecipe(recipeId, "Bearer " + token);
        }

        call.enqueue(new Callback<Void>() {
            @Override
            public void onResponse(Call<Void> call, Response<Void> response) {
                if (response.isSuccessful()) {
                    boolean newLikeState = !likeIconView.isSelected();
                    updateLikeButton(newLikeState);
                    int currentLikes = Integer.parseInt(likesCount.getText().toString());
                    likesCount.setText(String.valueOf(newLikeState ? currentLikes + 1 : currentLikes - 1));
                } else {
                    Toast.makeText(RecipeDetailActivity.this, "Không thể cập nhật trạng thái yêu thích",
                            Toast.LENGTH_SHORT).show();
                }
            }

            @Override
            public void onFailure(Call<Void> call, Throwable t) {
                Toast.makeText(RecipeDetailActivity.this, "Lỗi kết nối: " + t.getMessage(), Toast.LENGTH_SHORT).show();
            }
        });
    }

    private void updateLikeButton(boolean isLiked) {
        likeIconView.setSelected(isLiked);
        int heartIcon = isLiked ? R.drawable.ic_heart_1 : R.drawable.icon_heart_1;
        likeIconView.setImageResource(heartIcon);
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
                        Comment newComment = new Comment(userId, commentDto.getUserName(), commentDto.getContent());
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

        Call<RecipeDto> call = apiService.getRecipe(recipeId, "Bearer " + token);
        call.enqueue(new Callback<RecipeDto>() {
            @Override
            public void onResponse(Call<RecipeDto> call, Response<RecipeDto> response) {
                progressDialog.dismiss();
                if (response.isSuccessful() && response.body() != null) {
                    RecipeDto recipe = response.body();
                    updateUI(recipe);
                    updateLikeButton(recipe.isLiked());
                    Log.e("RecipeDetail", recipe.isLiked() + "");
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
            comments.add(
                    new Comment(commentDto.getUserId().toString(), commentDto.getUserName(), commentDto.getContent()));
        }
        commentAdapter.setComments(comments);

        isOwnRecipe = currentUserId.equals(recipe.getUserId().toString());

        editButton.setVisibility(isOwnRecipe ? View.VISIBLE : View.GONE);
        deleteButton.setVisibility(isOwnRecipe ? View.VISIBLE : View.GONE);
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

    @Override
    public void onAvatarClick(String userId) {
        // Chuyển đến ProfileActivity với userId
        Intent intent = new Intent(this, ProfileActivity.class);
        intent.putExtra("USER_ID", userId);
        startActivity(intent);
    }

    private void checkBookmarkStatus() {
        String token = getSharedPreferences("MyAppPrefs", MODE_PRIVATE).getString("token", "");
        RetrofitClient.getInstance().getApiService()
                .getBookmarkStatus("Bearer " + token, recipeId)
                .enqueue(new Callback<BookmarkResponse>() {
                    @Override
                    public void onResponse(Call<BookmarkResponse> call, Response<BookmarkResponse> response) {
                        if (response.isSuccessful() && response.body() != null) {
                            isBookmarked = response.body().isBookmarked();
                            Log.e("RecipeDetailActivity,Bookmark", String.valueOf(isBookmarked));
                            updateBookmarkIcon();
                        } else {
                            Log.e("RecipeDetailActivity", "Error: " + response.code());
                        }
                    }

                    @Override
                    public void onFailure(Call<BookmarkResponse> call, Throwable t) {
                        Log.e("RecipeDetailActivity", "Error: " + t.getMessage());
                        Toast.makeText(RecipeDetailActivity.this,
                                "Lỗi kiểm tra trạng thái bookmark", Toast.LENGTH_SHORT).show();
                    }
                });
    }

    private void toggleBookmark() {
        String token = getSharedPreferences("MyAppPrefs", MODE_PRIVATE).getString("token", "");
        boolean isAddBookmark = !isBookmarked; // Kiểm tra xem có phải thêm bookmark không

        // Chọn phương thức thêm hoặc xóa bookmark tùy vào trạng thái hiện tại
        String action = isAddBookmark ? "addBookmark" : "removeBookmark";
        Call<Void> call = getBookmarkCall(token, recipeId, action);

        call.enqueue(new Callback<Void>() {
            @Override
            public void onResponse(Call<Void> call, Response<Void> response) {
                if (!response.isSuccessful()) {
                    handleErrorResponse(response);
                    return;
                }

                // Cập nhật trạng thái và icon bookmark
                isBookmarked = isAddBookmark;
                updateBookmarkIcon();
                String message = isAddBookmark ? "Đã lưu công thức" : "Đã bỏ lưu công thức";
                Toast.makeText(RecipeDetailActivity.this, message, Toast.LENGTH_SHORT).show();
            }

            @Override
            public void onFailure(Call<Void> call, Throwable t) {
                handleFailure(t); // Xử lý lỗi
            }
        });
    }

    private Call<Void> getBookmarkCall(String token, String recipeId, String action) {
        if ("addBookmark".equals(action)) {
            return RetrofitClient.getInstance().getApiService().addBookmark("Bearer " + token, recipeId);
        } else {
            return RetrofitClient.getInstance().getApiService().removeBookmark("Bearer " + token, recipeId);
        }
    }


    private void handleFailure(Throwable t) {
        System.out.println("Request failed: " + t.getMessage());
        if (t instanceof EOFException) {
            // Xử lý khi phản hồi trống
            Toast.makeText(RecipeDetailActivity.this,
                    "Lỗi: Server trả về phản hồi trống", Toast.LENGTH_SHORT).show();
        } else {
            Log.e("RecipeDetailActivity", "Network error", t);
            Toast.makeText(RecipeDetailActivity.this,
                    "Lỗi kết nối: " + t.getMessage(), Toast.LENGTH_SHORT).show();
        }
    }

    private void handleErrorResponse(Response<?> response) {
        try {
            String errorBody = response.errorBody() != null ?
                    response.errorBody().string() : "Unknown error";
            Log.e("RecipeDetailActivity", "Error body: " + errorBody);
            Toast.makeText(RecipeDetailActivity.this,
                    "Lỗi: " + errorBody, Toast.LENGTH_SHORT).show();
        } catch (IOException e) {
            Log.e("RecipeDetailActivity", "Error reading error body", e);
            Toast.makeText(RecipeDetailActivity.this,
                    "Lỗi: " + response.code(), Toast.LENGTH_SHORT).show();
        }
    }

    private void updateBookmarkIcon() {
        bookmarkButton.setImageResource(isBookmarked ?
                R.drawable.baseline_bookmark_24 : R.drawable.baseline_bookmark_border_24);
    }

    private void editRecipe() {
        Intent intent = new Intent(this, EditRecipeActivity.class);
        intent.putExtra("RECIPE_ID", recipeId);
        startActivity(intent);
    }

    private void deleteRecipe() {
        new AlertDialog.Builder(this)
            .setTitle("Xác nhận xóa")
            .setMessage("Bạn có chắc chắn muốn xóa công thức này?")
            .setPositiveButton("Xóa", (dialog, which) -> {
                performDeleteRecipe();
            })
            .setNegativeButton("Hủy", null)
            .show();
    }

    private void performDeleteRecipe() {
        // Hiển thị dialog loading
        ProgressDialog progressDialog = new ProgressDialog(this);
        progressDialog.setMessage("Đang xóa công thức...");
        progressDialog.setCancelable(false);
        progressDialog.show();

        Call<Void> call = apiService.deleteRecipe(recipeId, "Bearer " + token);
        call.enqueue(new Callback<Void>() {
            @Override
            public void onResponse(Call<Void> call, Response<Void> response) {
                progressDialog.dismiss();
                if (response.isSuccessful()) {
                    Toast.makeText(RecipeDetailActivity.this, 
                        "Đã xóa công thức thành công", Toast.LENGTH_SHORT).show();
                    setResult(RESULT_OK); // Để activity trước có thể biết và refresh
                    finish();
                } else {
                    if (response.code() == 404) {
                        Toast.makeText(RecipeDetailActivity.this, 
                            "Không tìm thấy công thức", Toast.LENGTH_SHORT).show();
                    } else {
                        Toast.makeText(RecipeDetailActivity.this, 
                            "Không thể xóa công thức", Toast.LENGTH_SHORT).show();
                    }
                }
            }

            @Override
            public void onFailure(Call<Void> call, Throwable t) {
                progressDialog.dismiss();
                Toast.makeText(RecipeDetailActivity.this, 
                    "Lỗi kết nối: " + t.getMessage(), Toast.LENGTH_SHORT).show();
            }
        });
    }
}
