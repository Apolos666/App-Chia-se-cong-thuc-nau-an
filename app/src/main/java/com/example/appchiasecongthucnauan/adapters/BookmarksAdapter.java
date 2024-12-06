package com.example.appchiasecongthucnauan.adapters;

import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ImageView;
import android.widget.TextView;

import androidx.annotation.NonNull;
import androidx.recyclerview.widget.RecyclerView;

import com.bumptech.glide.Glide;
import com.example.appchiasecongthucnauan.R;
import com.example.appchiasecongthucnauan.activities.RecipeDetailActivity;
import com.example.appchiasecongthucnauan.models.Bookmark;

import java.util.ArrayList;
import java.util.List;

public class BookmarksAdapter extends RecyclerView.Adapter<BookmarksAdapter.ViewHolder> {
    private List<Bookmark> bookmarks;
    private List<Bookmark> bookmarksFiltered;
    private OnBookmarkActionListener listener;

    @FunctionalInterface
    public interface OnBookmarkActionListener {
        void onMoreAction(Bookmark bookmark);
    }

    public BookmarksAdapter(List<Bookmark> bookmarks, OnBookmarkActionListener listener) {
        this.bookmarks = bookmarks;
        this.bookmarksFiltered = new ArrayList<>(bookmarks);
        this.listener = listener;
    }

    public void filter(String query) {
        bookmarksFiltered.clear();
        if (query.isEmpty()) {
            bookmarksFiltered.addAll(bookmarks);
        } else {
            String lowerCaseQuery = query.toLowerCase().trim();
            for (Bookmark bookmark : bookmarks) {
                if (bookmark.getTitle().toLowerCase().contains(lowerCaseQuery) ||
                    bookmark.getAuthor().toLowerCase().contains(lowerCaseQuery)) {
                    bookmarksFiltered.add(bookmark);
                }
            }
        }
        notifyDataSetChanged();
    }

    @Override
    public ViewHolder onCreateViewHolder(@NonNull ViewGroup parent, int viewType) {
        View view = LayoutInflater.from(parent.getContext()).inflate(R.layout.item_bookmark, parent, false);
        return new ViewHolder(view);
    }

    @Override
    public void onBindViewHolder(@NonNull ViewHolder holder, int position) {
        Bookmark bookmark = bookmarksFiltered.get(position);
        holder.title.setText(bookmark.getTitle());
        holder.author.setText(bookmark.getAuthor());
        
        // Load image using Glide
        if (bookmark.getMediaUrl() != null && !bookmark.getMediaUrl().isEmpty()) {
            Glide.with(holder.itemView.getContext())
                .load(bookmark.getMediaUrl())
                .placeholder(R.drawable.ic_placeholder)
                .error(R.drawable.ic_error)
                .into(holder.bookmarkImage);
        }

        holder.itemView.setOnClickListener(v -> {
            // Navigate to recipe detail
            RecipeDetailActivity.start(
                holder.itemView.getContext(), 
                bookmark.getRecipeId()
            );
        });
    }

    @Override
    public int getItemCount() {
        return bookmarksFiltered.size();
    }

    public void setBookmarks(List<Bookmark> bookmarks) {
        this.bookmarks = bookmarks;
        this.bookmarksFiltered = new ArrayList<>(bookmarks);
        notifyDataSetChanged();
    }

    static class ViewHolder extends RecyclerView.ViewHolder {
        TextView title;
        TextView author;
        ImageView bookmarkImage;

        ViewHolder(View itemView) {
            super(itemView);
            title = itemView.findViewById(R.id.bookmarkTitle);
            author = itemView.findViewById(R.id.bookmarkAuthor);
            bookmarkImage = itemView.findViewById(R.id.bookmarkImage);
        }
    }
}