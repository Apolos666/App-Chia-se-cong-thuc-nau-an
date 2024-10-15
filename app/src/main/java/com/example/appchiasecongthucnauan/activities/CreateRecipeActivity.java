package com.example.appchiasecongthucnauan.activities;

import android.os.Bundle;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;
import android.widget.ImageButton;
import android.widget.LinearLayout;
import android.widget.Toast;
import com.example.appchiasecongthucnauan.R;
import androidx.appcompat.app.AppCompatActivity;

public class CreateRecipeActivity extends AppCompatActivity {

    private EditText recipeNameEditText;
    private EditText recipeDescriptionEditText;
    private LinearLayout ingredientsLayout;
    private Button addIngredientButton;
    private Button publishButton;
    private ImageButton backButton;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_create_recipe);

        // Initialize UI components
        recipeNameEditText = findViewById(R.id.recipeNameEditText);
        recipeDescriptionEditText = findViewById(R.id.recipeDescriptionEditText);
        ingredientsLayout = findViewById(R.id.ingredientsLayout);
        addIngredientButton = findViewById(R.id.addIngredientButton);
        publishButton = findViewById(R.id.publishButton);
        backButton = findViewById(R.id.backButton);

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
    }

    private void addIngredientField() {
        EditText ingredientEditText = new EditText(this);
        ingredientEditText.setHint("Nguyên liệu " + (ingredientsLayout.getChildCount() + 1));
        LinearLayout.LayoutParams params = new LinearLayout.LayoutParams(
                LinearLayout.LayoutParams.MATCH_PARENT,
                LinearLayout.LayoutParams.WRAP_CONTENT
        );
        params.setMargins(0, 16, 0, 0);
        ingredientEditText.setLayoutParams(params);
        ingredientsLayout.addView(ingredientEditText);
    }

    private void publishRecipe() {
        String name = recipeNameEditText.getText().toString().trim();
        String description = recipeDescriptionEditText.getText().toString().trim();

        if (name.isEmpty() || description.isEmpty()) {
            Toast.makeText(this, "Please fill in all fields", Toast.LENGTH_SHORT).show();
            return;
        }

        // TODO: Implement the logic to save the recipe
        // This could involve saving to a local database or sending to a server

        Toast.makeText(this, "Recipe published successfully", Toast.LENGTH_SHORT).show();
        finish(); // Close the activity after publishing
    }
}
