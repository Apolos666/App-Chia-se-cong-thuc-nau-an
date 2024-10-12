package com.example.appchiasecongthucnauan.adapters;

import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.TextView;

import androidx.annotation.NonNull;
import androidx.recyclerview.widget.RecyclerView;

import com.example.appchiasecongthucnauan.R;
import com.example.appchiasecongthucnauan.models.BookmarkCollection;

import java.util.List;

public class CollectionsAdapter extends RecyclerView.Adapter<CollectionsAdapter.ViewHolder> {
    private List<BookmarkCollection> collections;

    public CollectionsAdapter(List<BookmarkCollection> collections) {
        this.collections = collections;
    }

    @Override
    public ViewHolder onCreateViewHolder(@NonNull ViewGroup parent, int viewType) {
        View view = LayoutInflater.from(parent.getContext()).inflate(R.layout.item_collection, parent, false);
        return new ViewHolder(view);
    }

    @Override
    public void onBindViewHolder(@NonNull ViewHolder holder, int position) {
        BookmarkCollection collection = collections.get(position);
        holder.collectionName.setText(collection.getName());
        holder.recipeCount.setText(collection.getRecipeCount() + " recipes");
    }

    @Override
    public int getItemCount() {
        return collections.size();
    }

    static class ViewHolder extends RecyclerView.ViewHolder {
        TextView collectionName;
        TextView recipeCount;

        ViewHolder(View itemView) {
            super(itemView);
            collectionName = itemView.findViewById(R.id.collectionName);
            recipeCount = itemView.findViewById(R.id.recipeCount);
        }
    }
}