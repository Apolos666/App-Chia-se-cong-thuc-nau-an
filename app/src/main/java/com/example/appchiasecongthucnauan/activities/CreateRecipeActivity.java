package com.example.appchiasecongthucnauan.activities;

import android.database.Cursor;
import android.os.Bundle;
import android.provider.OpenableColumns;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;
import android.widget.ImageButton;
import android.widget.LinearLayout;
import android.widget.Toast;
import android.content.DialogInterface;
import android.content.Intent;
import android.net.Uri;
import android.widget.ImageView;
import android.widget.VideoView;
import android.widget.MediaController;
import android.widget.Spinner;
import android.widget.ArrayAdapter;

import androidx.annotation.Nullable;
import androidx.appcompat.app.AlertDialog;
import androidx.appcompat.app.AppCompatActivity;

import com.example.appchiasecongthucnauan.R;
import com.example.appchiasecongthucnauan.apis.ApiService;
import com.example.appchiasecongthucnauan.models.RecipeCategoryDto;
import com.example.appchiasecongthucnauan.utils.RetrofitClient;

import java.io.IOException;
import java.io.InputStream;
import java.util.ArrayList;
import java.util.List;
import java.util.UUID;

import okhttp3.MediaType;
import retrofit2.Call;
import retrofit2.Callback;
import retrofit2.Response;
import retrofit2.Retrofit;
import retrofit2.converter.gson.GsonConverterFactory;
import android.app.ProgressDialog;

import org.apache.commons.io.IOUtils;

import okhttp3.MultipartBody;
import okhttp3.RequestBody;

import android.util.Log;
import android.content.SharedPreferences;

import okhttp3.OkHttpClient;
import okhttp3.Request;

import java.util.concurrent.TimeUnit;

public class CreateRecipeActivity extends AppCompatActivity {

    private EditText recipeNameEditText;
    private LinearLayout ingredientsLayout;
    private Button addIngredientButton;
    private Button publishButton;
    private ImageButton backButton;
    private static final int PICK_IMAGE_REQUEST = 1;
    private static final int PICK_VIDEO_REQUEST = 2;
    private LinearLayout mediaContainer;
    private Button addMediaButton;
    private Button addInstructionButton;
    private Spinner categorySpinner;
    private List<RecipeCategoryDto> categories;
    private ArrayAdapter<String> categoryAdapter;
    private ApiService apiService;
    private LinearLayout instructionsLayout;
    private List<Uri> mediaUris = new ArrayList<>();

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_create_recipe);

        // Initialize UI components
        recipeNameEditText = findViewById(R.id.recipeNameEditText);
        ingredientsLayout = findViewById(R.id.ingredientsLayout);
        addIngredientButton = findViewById(R.id.addIngredientButton);
        publishButton = findViewById(R.id.publishButton);
        backButton = findViewById(R.id.backButton);
        mediaContainer = findViewById(R.id.mediaContainer);
        addMediaButton = findViewById(R.id.addMediaButton);
        addInstructionButton = findViewById(R.id.addInstructionButton);
        categorySpinner = findViewById(R.id.categorySpinner);
        instructionsLayout = findViewById(R.id.instructionsLayout);

        setupRetrofit();
        fetchRecipeCategories();

        // Set up click listeners
        addIngredientButton.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                addIngredientField();
            }
        });

        publishButton.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                publishRecipe();
            }
        });

        backButton.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                finish();
            }
        });

        // Thêm sự kiện click cho nút thêm media
        addMediaButton.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                showMediaPickerDialog();
            }
        });

        addInstructionButton.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                addInstructionField();
            }
        });
    }

    private void addIngredientField() {
        EditText ingredientEditText = new EditText(this);
        ingredientEditText.setHint("Nguyên liệu " + (ingredientsLayout.getChildCount() + 1));
        LinearLayout.LayoutParams params = new LinearLayout.LayoutParams(
                LinearLayout.LayoutParams.MATCH_PARENT,
                LinearLayout.LayoutParams.WRAP_CONTENT);
        params.setMargins(0, 16, 0, 0);
        ingredientEditText.setLayoutParams(params);
        ingredientsLayout.addView(ingredientEditText);
    }

    private void publishRecipe() {
        ProgressDialog progressDialog = new ProgressDialog(this);
        progressDialog.setMessage("Đang đăng công thức...");
        progressDialog.setCancelable(false);
        progressDialog.show();

        String token = getToken();

        RequestBody title = RequestBody.create(MediaType.parse("text/plain"),
                recipeNameEditText.getText().toString().trim());

        String ingredientsString = getIngredientsString();
        RequestBody ingredients = RequestBody.create(MediaType.parse("text/plain"), ingredientsString);

        String instructionsString = getInstructionsString();
        RequestBody instructions = RequestBody.create(MediaType.parse("text/plain"), instructionsString);

        String categoryId = getCategoryId().toString();
        RequestBody recipeCategoryId = RequestBody.create(MediaType.parse("text/plain"), categoryId);

        List<MultipartBody.Part> mediaFiles = getMediaFiles();

        Call<Void> call = apiService.createRecipe("Bearer " + token, title, ingredients, instructions, recipeCategoryId,
                mediaFiles);

        call.enqueue(new Callback<Void>() {
            @Override
            public void onResponse(Call<Void> call, Response<Void> response) {
                progressDialog.dismiss();
                if (response.isSuccessful()) {
                    Toast.makeText(CreateRecipeActivity.this, "Công thức đã được đăng thành công", Toast.LENGTH_SHORT)
                            .show();
                    finish();
                } else {
                    Log.e("CreateRecipeActivity", "Lỗi khi đăng công thức. Mã lỗi: " + response.code());
                    Log.e("CreateRecipeActivity", "Nội dung lỗi: " + response.errorBody().toString());
                    Toast.makeText(CreateRecipeActivity.this, "Lỗi khi đăng công thức", Toast.LENGTH_SHORT).show();
                }
            }

            @Override
            public void onFailure(Call<Void> call, Throwable t) {
                progressDialog.dismiss();
                Log.e("CreateRecipeActivity", "Lỗi kết nối: " + t.getMessage());
                Toast.makeText(CreateRecipeActivity.this, "Lỗi kết nối: " + t.getMessage(), Toast.LENGTH_SHORT).show();
            }
        });
    }

    private String getIngredientsString() {
        StringBuilder ingredients = new StringBuilder();
        for (int i = 0; i < ingredientsLayout.getChildCount(); i++) {
            EditText ingredientEditText = (EditText) ingredientsLayout.getChildAt(i);
            ingredients.append(ingredientEditText.getText().toString()).append("\n");
        }
        return ingredients.toString().trim();
    }

    private String getInstructionsString() {
        StringBuilder instructions = new StringBuilder();
        for (int i = 0; i < instructionsLayout.getChildCount(); i++) {
            EditText instructionEditText = (EditText) instructionsLayout.getChildAt(i);
            instructions.append(instructionEditText.getText().toString()).append("\n");
        }
        return instructions.toString().trim();
    }

    private String getCategoryId() {
        RecipeCategoryDto selectedCategory = categories.get(categorySpinner.getSelectedItemPosition());
        return selectedCategory.getId();
    }

    private List<MultipartBody.Part> getMediaFiles() {
        List<MultipartBody.Part> parts = new ArrayList<>();
        for (Uri uri : mediaUris) {
            try {
                String mimeType = getContentResolver().getType(uri);
                String fileName = getFileName(uri);
                InputStream inputStream = getContentResolver().openInputStream(uri);
                if (inputStream != null) {
                    byte[] bytes = IOUtils.toByteArray(inputStream);
                    RequestBody requestFile = RequestBody.create(MediaType.parse(mimeType), bytes);
                    MultipartBody.Part part = MultipartBody.Part.createFormData("mediaFiles", fileName, requestFile);
                    parts.add(part);
                }
            } catch (IOException e) {
                e.printStackTrace();
            }
        }
        return parts;
    }

    private String getFileName(Uri uri) {
        String result = null;
        if (uri.getScheme().equals("content")) {
            Cursor cursor = getContentResolver().query(uri, null, null, null, null);
            try {
                if (cursor != null && cursor.moveToFirst()) {
                    int index = cursor.getColumnIndex(OpenableColumns.DISPLAY_NAME);
                    if (index != -1) {
                        result = cursor.getString(index);
                    }
                }
            } finally {
                if (cursor != null) {
                    cursor.close();
                }
            }
        }
        if (result == null) {
            result = uri.getPath();
            int cut = result.lastIndexOf('/');
            if (cut != -1) {
                result = result.substring(cut + 1);
            }
        }
        return result;
    }

    private void showMediaPickerDialog() {
        AlertDialog.Builder builder = new AlertDialog.Builder(this);
        builder.setTitle("Chọn loại media");
        String[] options = { "Ảnh", "Video" };
        builder.setItems(options, new DialogInterface.OnClickListener() {
            @Override
            public void onClick(DialogInterface dialog, int which) {
                if (which == 0) {
                    openImagePicker();
                } else {
                    openVideoPicker();
                }
            }
        });
        builder.show();
    }

    private void openImagePicker() {
        Intent intent = new Intent();
        intent.setType("image/*");
        intent.setAction(Intent.ACTION_GET_CONTENT);
        startActivityForResult(Intent.createChooser(intent, "Chọn ảnh"), PICK_IMAGE_REQUEST);
    }

    private void openVideoPicker() {
        Intent intent = new Intent();
        intent.setType("video/*");
        intent.setAction(Intent.ACTION_GET_CONTENT);
        startActivityForResult(Intent.createChooser(intent, "Chọn video"), PICK_VIDEO_REQUEST);
    }

    @Override
    protected void onActivityResult(int requestCode, int resultCode, @Nullable Intent data) {
        super.onActivityResult(requestCode, resultCode, data);
        if (resultCode == RESULT_OK && data != null && data.getData() != null) {
            if (requestCode == PICK_IMAGE_REQUEST) {
                addImageToContainer(data.getData());
            } else if (requestCode == PICK_VIDEO_REQUEST) {
                addVideoToContainer(data.getData());
            }
        }
    }

    private void addImageToContainer(Uri imageUri) {
        ImageView imageView = new ImageView(this);
        imageView.setLayoutParams(new LinearLayout.LayoutParams(
                LinearLayout.LayoutParams.MATCH_PARENT,
                500));
        imageView.setScaleType(ImageView.ScaleType.CENTER_CROP);
        imageView.setImageURI(imageUri);
        mediaContainer.addView(imageView);
        mediaUris.add(imageUri);
    }

    private void addVideoToContainer(Uri videoUri) {
        VideoView videoView = new VideoView(this);
        videoView.setLayoutParams(new LinearLayout.LayoutParams(
                LinearLayout.LayoutParams.MATCH_PARENT,
                500));
        videoView.setVideoURI(videoUri);
        MediaController mediaController = new MediaController(this);
        mediaController.setAnchorView(videoView);
        videoView.setMediaController(mediaController);
        mediaContainer.addView(videoView);
        mediaUris.add(videoUri);
    }

    private void addInstructionField() {
        EditText instructionEditText = new EditText(this);
        instructionEditText.setHint("Bước " + (instructionsLayout.getChildCount() + 1));
        LinearLayout.LayoutParams params = new LinearLayout.LayoutParams(
                LinearLayout.LayoutParams.MATCH_PARENT,
                LinearLayout.LayoutParams.WRAP_CONTENT);
        params.setMargins(0, 16, 0, 0);
        instructionEditText.setLayoutParams(params);
        instructionsLayout.addView(instructionEditText);
    }

    private void fetchRecipeCategories() {
        Call<List<RecipeCategoryDto>> call = apiService.getRecipeCategories();
        call.enqueue(new Callback<List<RecipeCategoryDto>>() {
            @Override
            public void onResponse(Call<List<RecipeCategoryDto>> call, Response<List<RecipeCategoryDto>> response) {
                if (response.isSuccessful() && response.body() != null) {
                    categories = response.body();
                    updateCategorySpinner();
                } else {
                    Toast.makeText(CreateRecipeActivity.this, "Không thể lấy danh sách loại công thức",
                            Toast.LENGTH_SHORT).show();
                }
            }

            @Override
            public void onFailure(Call<List<RecipeCategoryDto>> call, Throwable t) {
                Toast.makeText(CreateRecipeActivity.this, "Lỗi kết nối: " + t.getMessage(), Toast.LENGTH_SHORT).show();
            }
        });
    }

    private void updateCategorySpinner() {
        List<String> categoryNames = new ArrayList<>();
        for (RecipeCategoryDto category : categories) {
            categoryNames.add(category.getCategoryName());
        }
        categoryAdapter = new ArrayAdapter<>(this, android.R.layout.simple_spinner_item, categoryNames);
        categoryAdapter.setDropDownViewResource(android.R.layout.simple_spinner_dropdown_item);
        categorySpinner.setAdapter(categoryAdapter);
    }

    private String getToken() {
        SharedPreferences sharedPreferences = getSharedPreferences("MyAppPrefs", MODE_PRIVATE);
        return sharedPreferences.getString("token", "");
    }

    private void setupRetrofit() {
        apiService = RetrofitClient.getInstance().getApiService();
    }
}
