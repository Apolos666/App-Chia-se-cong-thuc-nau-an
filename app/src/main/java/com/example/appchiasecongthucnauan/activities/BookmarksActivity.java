package com.example.appchiasecongthucnauan.activities;

import android.content.Intent;
import android.os.Bundle;
import android.text.Editable;
import android.text.TextWatcher;
import android.widget.EditText;

import androidx.appcompat.app.AppCompatActivity;
import androidx.lifecycle.ViewModelProvider;
import androidx.recyclerview.widget.LinearLayoutManager;
import androidx.recyclerview.widget.RecyclerView;

import com.example.appchiasecongthucnauan.R;
import com.example.appchiasecongthucnauan.adapters.BookmarksAdapter;
import com.example.appchiasecongthucnauan.adapters.CollectionsAdapter;
import com.example.appchiasecongthucnauan.fragments.ChooseCollectionDialog;
import com.example.appchiasecongthucnauan.models.Bookmark;
import com.example.appchiasecongthucnauan.models.BookmarkCollection;
import com.example.appchiasecongthucnauan.models.BookmarkDto;
import com.example.appchiasecongthucnauan.utils.RetrofitClient;
import com.google.android.material.bottomsheet.BottomSheetDialog;

import android.widget.ImageButton;
import android.widget.Toast;

import java.util.ArrayList;
import java.util.List;

import retrofit2.Call;
import retrofit2.Callback;
import retrofit2.Response;

public class BookmarksActivity extends AppCompatActivity {
    private EditText searchBookmarks;
    private RecyclerView bookmarksRecyclerView;
    private List<BookmarkCollection> collections;
    private List<Bookmark> bookmarks;
    private BottomSheetDialog bottomSheetDialog;
    private ImageButton backButton;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_bookmarks);

        initViews();
        loadBookmarks();
    }

    private void initViews() {
        searchBookmarks = findViewById(R.id.searchBookmarks);
        bookmarksRecyclerView = findViewById(R.id.bookmarksRecyclerView);
        backButton = findViewById(R.id.btnBack);
        
        bookmarksRecyclerView.setLayoutManager(new LinearLayoutManager(this));
        backButton.setOnClickListener(v -> finish());
    }

    private void loadBookmarks() {
        String token = getSharedPreferences("MyAppPrefs", MODE_PRIVATE)
            .getString("token", "");

        RetrofitClient.getInstance().getApiService()
            .getBookmarks("Bearer " + token)
            .enqueue(new Callback<List<BookmarkDto>>() {
                @Override
                public void onResponse(Call<List<BookmarkDto>> call,
                    Response<List<BookmarkDto>> response) {
                    if (response.isSuccessful() && response.body() != null) {
                        List<Bookmark> bookmarks = new ArrayList<>();
                        for (BookmarkDto dto : response.body()) {
                            bookmarks.add(new Bookmark(
                                dto.getTitle(),
                                dto.getCreatorName(),
                                dto.getMediaUrl(),
                                dto.getRecipeId()
                            ));
                        }
                        setupBookmarksAdapter(bookmarks);
                    } else {
                        Toast.makeText(BookmarksActivity.this, 
                            "Không thể tải bookmark", Toast.LENGTH_SHORT).show();
                    }
                }

                @Override
                public void onFailure(Call<List<BookmarkDto>> call, Throwable t) {
                    Toast.makeText(BookmarksActivity.this,
                        "Lỗi kết nối: " + t.getMessage(), 
                        Toast.LENGTH_SHORT).show();
                }
            });
    }

    private void setupBookmarksAdapter(List<Bookmark> bookmarks) {
        BookmarksAdapter adapter = new BookmarksAdapter(
            bookmarks,
            bookmark -> showChooseCollectionDialog(bookmark)
        );
        bookmarksRecyclerView.setAdapter(adapter);
    }

    private void showChooseCollectionDialog(Bookmark bookmark) {
        ChooseCollectionDialog dialog = new ChooseCollectionDialog(collections, selectedCollection -> {
            // TODO: Implement moving bookmark to selected collection
            Toast.makeText(this, "Moved " + bookmark.getTitle(), Toast.LENGTH_SHORT).show();
        });
        dialog.show(getSupportFragmentManager(), "chooseCollection");
    }
}
