package com.example.appchiasecongthucnauan.adapters;

import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ImageView;
import android.widget.TextView;

import androidx.annotation.NonNull;
import androidx.recyclerview.widget.RecyclerView;

import com.example.appchiasecongthucnauan.R;
import com.example.appchiasecongthucnauan.models.Bookmark;

import java.util.List;

public class BookmarksAdapter extends RecyclerView.Adapter<BookmarksAdapter.ViewHolder> {
    private List<Bookmark> bookmarks;
    private OnBookmarkActionListener listener;

    @FunctionalInterface
    public interface OnBookmarkActionListener {
        void onMoreAction(Bookmark bookmark);
    }

    public BookmarksAdapter(List<Bookmark> bookmarks, OnBookmarkActionListener listener) {
        this.bookmarks = bookmarks;
        this.listener = listener;
    }

    @Override
    public ViewHolder onCreateViewHolder(@NonNull ViewGroup parent, int viewType) {
        View view = LayoutInflater.from(parent.getContext()).inflate(R.layout.item_bookmark, parent, false);
        return new ViewHolder(view);
    }

    @Override
    public void onBindViewHolder(@NonNull ViewHolder holder, int position) {
        Bookmark bookmark = bookmarks.get(position);
        holder.title.setText(bookmark.getTitle());
        holder.author.setText(bookmark.getAuthor());
    }

    @Override
    public int getItemCount() {
        return bookmarks.size();
    }

    static class ViewHolder extends RecyclerView.ViewHolder {
        TextView title;
        TextView author;

        ViewHolder(View itemView) {
            super(itemView);
            title = itemView.findViewById(R.id.bookmarkTitle);
            author = itemView.findViewById(R.id.bookmarkAuthor);
        }
    }
}