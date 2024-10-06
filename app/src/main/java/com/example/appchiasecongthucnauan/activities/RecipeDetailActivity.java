package com.example.appchiasecongthucnauan.activities;

import android.os.Bundle;
import android.widget.EditText;
import android.widget.ImageView;
import android.widget.TextView;

import androidx.annotation.Nullable;
import androidx.appcompat.app.AppCompatActivity;
import androidx.recyclerview.widget.LinearLayoutManager;
import androidx.recyclerview.widget.RecyclerView;

import com.example.appchiasecongthucnauan.R;
import com.example.appchiasecongthucnauan.adapters.CommentAdapter;
import com.example.appchiasecongthucnauan.models.Comment;
import com.example.appchiasecongthucnauan.models.Recipe;

import java.util.ArrayList;
import java.util.List;

public class RecipeDetailActivity extends AppCompatActivity {
    private ImageView backButton, likeButton;
    private TextView recipeName, chefName, likesCount, commentsCount;
    private TextView ingredientsText, instructionsText;
    private RecyclerView commentsRecyclerView;
    private EditText commentInput;
    private ImageView sendCommentButton;
    private CommentAdapter commentAdapter;

    @Override
    protected void onCreate(@Nullable Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_recipe_detail);

        initViews();
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

        backButton.setOnClickListener(v -> finish());
        likeButton.setOnClickListener(v -> toggleLike());
        sendCommentButton.setOnClickListener(v -> sendComment());
    }

    private void setupRecipe() {
        // In a real app, you'd get this data from an intent or a database
        Recipe recipe = new Recipe("Pasta Carbonara", "Chef John", 256, 42);

        recipeName.setText(recipe.getName());
        chefName.setText(recipe.getChef());
        likesCount.setText(String.valueOf(recipe.getLikes()));
        commentsCount.setText(String.valueOf(recipe.getComments()));

        // Set ingredients and instructions
        ingredientsText.setText("400g spaghetti\n200g pancetta\n4 large eggs\n100g Pecorino cheese\n100g Parmesan\nFreshly ground black pepper");
        instructionsText.setText("1. Cook spaghetti in salted boiling water\n2. Fry pancetta until crispy.\n3. Beat eggs with grated cheeses.\n4. Toss hot pasta with pancetta, then egg mixture.");
    }

    private void setupComments() {
        List<Comment> comments = new ArrayList<>();
        comments.add(new Comment("Haley", "Yum yum!"));
        comments.add(new Comment("Bob", "Loved this recipe!"));
        comments.add(new Comment("Haley", "I added some garlic. It was amazing!"));
        comments.add(new Comment("Bob", "My kids love it!"));

        commentAdapter = new CommentAdapter(comments);
        commentsRecyclerView.setLayoutManager(new LinearLayoutManager(this));
        commentsRecyclerView.setAdapter(commentAdapter);
    }

    private void toggleLike() {
        // Implement like functionality
    }

    private void sendComment() {
        String commentText = commentInput.getText().toString();
        if (!commentText.isEmpty()) {
            Comment newComment = new Comment("User", commentText);
            commentAdapter.addComment(newComment);
            commentInput.setText("");
        }
    }
}
