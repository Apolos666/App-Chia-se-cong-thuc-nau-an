package com.example.appchiasecongthucnauan.activities;

import android.os.Bundle;
import androidx.appcompat.app.AppCompatActivity;
import androidx.recyclerview.widget.LinearLayoutManager;
import androidx.recyclerview.widget.RecyclerView;

import com.example.appchiasecongthucnauan.R;
import com.example.appchiasecongthucnauan.adaptors.PostAdapter;
import com.example.appchiasecongthucnauan.models.Post;

import java.util.ArrayList;
import java.util.List;

public class HomePageActivity extends AppCompatActivity {

    private RecyclerView recyclerView;
    private PostAdapter postAdapter;
    private List<Post> postList;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_home);

        recyclerView = findViewById(R.id.recycler_view);
        recyclerView.setLayoutManager(new LinearLayoutManager(this));

        // Initialize post list and add sample data
        postList = new ArrayList<>();
        postList.add(new Post("Pasta Carbonara", "Chef John", 42, 12));
        postList.add(new Post("Grilled Salmon", "Chef Anna", 67, 18));
        postList.add(new Post("Beef Stroganoff", "Chef Mike", 120, 25));

        // Initialize the adapter and set it to the RecyclerView
        postAdapter = new PostAdapter(this, postList);
        recyclerView.setAdapter(postAdapter);
    }
}

