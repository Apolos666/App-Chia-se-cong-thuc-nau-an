package com.example.appchiasecongthucnauan.activities;

import android.app.Activity;
import android.content.Intent;
import android.content.SharedPreferences;
import android.os.Bundle;
import android.view.View;
import android.widget.ArrayAdapter;
import android.widget.Button;
import android.widget.EditText;
import android.widget.ImageButton;
import android.widget.Spinner;
import android.widget.Toast;

import androidx.activity.result.ActivityResultLauncher;
import androidx.activity.result.contract.ActivityResultContracts;
import androidx.appcompat.app.AppCompatActivity;

import com.example.appchiasecongthucnauan.R;
import com.example.appchiasecongthucnauan.apis.ApiService;
import com.example.appchiasecongthucnauan.models.RecipeCategoryDto;
import com.example.appchiasecongthucnauan.models.RecipeDto;
import com.example.appchiasecongthucnauan.models.UpdateRecipeDto;
import com.example.appchiasecongthucnauan.utils.RetrofitClient;

import java.util.ArrayList;
import java.util.List;
import java.util.UUID;

import retrofit2.Call;
import retrofit2.Callback;
import retrofit2.Response;

public class EditRecipeActivity extends AppCompatActivity {
    private EditText recipeNameEditText;
    private Spinner categorySpinner;
    private EditText ingredientsEditText;
    private EditText instructionsEditText;
    private Button publishButton;
    private ImageButton backButton;
    private ApiService apiService;
    private String token;
    private String recipeId;
    private List<RecipeCategoryDto> categories = new ArrayList<>();

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_edit_recipe);

        initViews();
        setupRetrofit();
        loadRecipeData();
        loadCategories();
    }

    private void initViews() {
        recipeNameEditText = findViewById(R.id.recipeNameEditText);
        categorySpinner = findViewById(R.id.categorySpinner);
        ingredientsEditText = findViewById(R.id.ingredientsEditText);
        instructionsEditText = findViewById(R.id.instructionsEditText);
        publishButton = findViewById(R.id.publishButton);
        backButton = findViewById(R.id.backButton);

        recipeId = getIntent().getStringExtra("RECIPE_ID");
        SharedPreferences sharedPreferences = getSharedPreferences("MyAppPrefs", MODE_PRIVATE);
        token = sharedPreferences.getString("token", "");

        publishButton.setOnClickListener(v -> updateRecipe());
        backButton.setOnClickListener(v -> finish());
    }

    private void setupRetrofit() {
        apiService = RetrofitClient.getInstance().getApiService();
    }

    private void loadRecipeData() {
        Call<RecipeDto> call = apiService.getRecipe(recipeId, "Bearer " + token);
        call.enqueue(new Callback<RecipeDto>() {
            @Override
            public void onResponse(Call<RecipeDto> call, Response<RecipeDto> response) {
                if (response.isSuccessful() && response.body() != null) {
                    RecipeDto recipe = response.body();
                    populateFields(recipe);
                } else {
                    Toast.makeText(EditRecipeActivity.this, "Không thể tải thông tin công thức", Toast.LENGTH_SHORT).show();
                }
            }

            @Override
            public void onFailure(Call<RecipeDto> call, Throwable t) {
                Toast.makeText(EditRecipeActivity.this, "Lỗi kết nối: " + t.getMessage(), Toast.LENGTH_SHORT).show();
            }
        });
    }

    private void loadCategories() {
        Call<List<RecipeCategoryDto>> call = apiService.getRecipeCategories();
        call.enqueue(new Callback<List<RecipeCategoryDto>>() {
            @Override
            public void onResponse(Call<List<RecipeCategoryDto>> call, Response<List<RecipeCategoryDto>> response) {
                if (response.isSuccessful() && response.body() != null) {
                    categories = response.body();
                    setupCategorySpinner();
                }
            }

            @Override
            public void onFailure(Call<List<RecipeCategoryDto>> call, Throwable t) {
                Toast.makeText(EditRecipeActivity.this, "Lỗi tải danh mục", Toast.LENGTH_SHORT).show();
            }
        });
    }

    private void populateFields(RecipeDto recipe) {
        recipeNameEditText.setText(recipe.getTitle());
        ingredientsEditText.setText(recipe.getIngredients());
        instructionsEditText.setText(recipe.getInstructions());

        // Chọn category trong spinner
        for (int i = 0; i < categories.size(); i++) {
            if (categories.get(i).getId().equals(recipe.getRecipeCategoryId())) {
                categorySpinner.setSelection(i);
                break;
            }
        }
    }

    private void setupCategorySpinner() {
        List<String> categoryNames = new ArrayList<>();
        for (RecipeCategoryDto category : categories) {
            categoryNames.add(category.getCategoryName());
        }
        ArrayAdapter<String> adapter = new ArrayAdapter<>(this, android.R.layout.simple_spinner_item, categoryNames);
        adapter.setDropDownViewResource(android.R.layout.simple_spinner_dropdown_item);
        categorySpinner.setAdapter(adapter);
    }

    private void updateRecipe() {
        String title = recipeNameEditText.getText().toString();
        String ingredients = ingredientsEditText.getText().toString();
        String instructions = instructionsEditText.getText().toString();
        UUID categoryId = UUID.fromString(categories.get(categorySpinner.getSelectedItemPosition()).getId());

        if (title.isEmpty() || ingredients.isEmpty() || instructions.isEmpty()) {
            Toast.makeText(this, "Vui lòng điền đầy đủ thông tin", Toast.LENGTH_SHORT).show();
            return;
        }

        UpdateRecipeDto updateRecipeDto = new UpdateRecipeDto(title, ingredients, instructions, categoryId);

        Call<Void> call = apiService.updateRecipe(recipeId, "Bearer " + token, updateRecipeDto);
        call.enqueue(new Callback<Void>() {
            @Override
            public void onResponse(Call<Void> call, Response<Void> response) {
                if (response.isSuccessful()) {
                    Toast.makeText(EditRecipeActivity.this, "Cập nhật công thức thành công", Toast.LENGTH_SHORT).show();
                    setResult(RESULT_OK);
                    finish();
                } else {
                    Toast.makeText(EditRecipeActivity.this, "Không thể cập nhật công thức", Toast.LENGTH_SHORT).show();
                }
            }

            @Override
            public void onFailure(Call<Void> call, Throwable t) {
                Toast.makeText(EditRecipeActivity.this, "Lỗi kết nối: " + t.getMessage(), Toast.LENGTH_SHORT).show();
            }
        });
    }
} 