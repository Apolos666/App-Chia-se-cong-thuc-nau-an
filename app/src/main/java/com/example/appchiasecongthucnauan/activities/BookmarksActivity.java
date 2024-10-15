package com.example.appchiasecongthucnauan.activities;

import android.os.Bundle;
import android.widget.EditText;

import androidx.appcompat.app.AppCompatActivity;
import androidx.recyclerview.widget.LinearLayoutManager;
import androidx.recyclerview.widget.RecyclerView;

import com.example.appchiasecongthucnauan.R;
import com.example.appchiasecongthucnauan.adapters.BookmarksAdapter;
import com.example.appchiasecongthucnauan.adapters.CollectionsAdapter;
import com.example.appchiasecongthucnauan.fragments.ChooseCollectionDialog;
import com.example.appchiasecongthucnauan.models.Bookmark;
import com.example.appchiasecongthucnauan.models.BookmarkCollection;
import com.google.android.material.bottomsheet.BottomSheetDialog;

import android.widget.ImageButton;
import android.widget.Toast;

import java.util.ArrayList;
import java.util.List;

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

        searchBookmarks = findViewById(R.id.searchBookmarks);

        bookmarksRecyclerView = findViewById(R.id.bookmarksRecyclerView);
        backButton = findViewById(R.id.btnBack);

        // Initialize data
        collections = new ArrayList<>();
        collections.add(new BookmarkCollection("Italian Favorites", 1));
        collections.add(new BookmarkCollection("Quick Meals", 2));
        collections.add(new BookmarkCollection("Desserts", 1));

        bookmarks = new ArrayList<>();
        bookmarks.add(new Bookmark("Summer Salad", "Chef Maria"));
        bookmarks.add(new Bookmark("Summer Salad", "Chef Maria"));
        bookmarks.add(new Bookmark("Summer Salad", "Chef Maria"));

        // Set up RecyclerViews
        bookmarksRecyclerView.setLayoutManager(new LinearLayoutManager(this));

        CollectionsAdapter collectionsAdapter = new CollectionsAdapter(collections);

//        BookmarksAdapter bookmarksAdapter = new BookmarksAdapter(bookmarks, bookmark -> showPopupMenu(bookmark));
        BookmarksAdapter bookmarksAdapter = new BookmarksAdapter(bookmarks, bookmark -> showChooseCollectionDialog(bookmark));

        bookmarksRecyclerView.setAdapter(bookmarksAdapter);


        backButton.setOnClickListener(v -> finish());
    }

//    private void showPopupMenu(Bookmark bookmark) {
//        PopupMenu popupMenu = new PopupMenu(this, findViewById(R.id.bookmarksRecyclerView)); // Vị trí của popup
//        for (BookmarkCollection collection : collections) {
//            popupMenu.getMenu().add(collection.getName()); // Thêm tên các collection vào menu
//        }
//
//        popupMenu.setOnMenuItemClickListener(item -> {
//            for (BookmarkCollection collection : collections) {
//                if (item.getTitle().equals(collection.getName())) {
//                    // TODO: Implement logic to move bookmark to selected collection
//                    Toast.makeText(this, "Moved " + bookmark.getTitle() + " to " + collection.getName(), Toast.LENGTH_SHORT).show();
//                    return true;
//                }
//            }
//            return false;
//        });
//
//        popupMenu.show();
//    }

    private void showChooseCollectionDialog(Bookmark bookmark) {
        ChooseCollectionDialog dialog = new ChooseCollectionDialog(collections, selectedCollection -> {
            // TODO: Implement moving bookmark to selected collection
            Toast.makeText(this, "Moved " + bookmark.getTitle(), Toast.LENGTH_SHORT).show();
        });
        dialog.show(getSupportFragmentManager(), "chooseCollection");
    }
}
