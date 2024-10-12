package com.example.appchiasecongthucnauan.activities;

import android.os.Bundle;
import android.widget.Button;
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

import android.widget.PopupMenu;
import android.widget.Toast;

import java.util.ArrayList;
import java.util.List;

public class BookmarksActivity extends AppCompatActivity {
    private EditText searchBookmarks;
    private Button newCollectionButton;
    private RecyclerView collectionsRecyclerView;
    private RecyclerView bookmarksRecyclerView;
    private List<BookmarkCollection> collections;
    private List<Bookmark> bookmarks;
    private BottomSheetDialog bottomSheetDialog;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_bookmarks);

        searchBookmarks = findViewById(R.id.searchBookmarks);
        newCollectionButton = findViewById(R.id.newCollectionButton);
        collectionsRecyclerView = findViewById(R.id.collectionsRecyclerView);
        bookmarksRecyclerView = findViewById(R.id.bookmarksRecyclerView);

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
        collectionsRecyclerView.setLayoutManager(new LinearLayoutManager(this));
        bookmarksRecyclerView.setLayoutManager(new LinearLayoutManager(this));

        CollectionsAdapter collectionsAdapter = new CollectionsAdapter(collections);
        collectionsRecyclerView.setAdapter(collectionsAdapter);

//        BookmarksAdapter bookmarksAdapter = new BookmarksAdapter(bookmarks, bookmark -> showPopupMenu(bookmark));
        BookmarksAdapter bookmarksAdapter = new BookmarksAdapter(bookmarks, bookmark -> showChooseCollectionDialog(bookmark));

        bookmarksRecyclerView.setAdapter(bookmarksAdapter);

        newCollectionButton.setOnClickListener(v -> {
            // TODO: Implement new collection creation
        });
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
