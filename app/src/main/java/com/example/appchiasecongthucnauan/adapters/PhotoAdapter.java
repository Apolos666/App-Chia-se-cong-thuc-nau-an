package com.example.appchiasecongthucnauan.adapters;

import android.content.Context;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ImageView;

import androidx.annotation.NonNull;
import androidx.recyclerview.widget.RecyclerView;

import com.example.appchiasecongthucnauan.R;

import java.util.List;

public class PhotoAdapter extends RecyclerView.Adapter<PhotoAdapter.PhotoViewHolder> {
    public static final int VIEW_TYPE_GRID = 0;
    public static final int VIEW_TYPE_LIST = 1;

    private Context context;
    private List<String> gridItems;
    private int viewType = VIEW_TYPE_GRID;

    public PhotoAdapter(Context context, List<String> gridItems) {
        this.context = context;
        this.gridItems = gridItems;
    }

    public void setViewType(int viewType) {
        this.viewType = viewType;
        notifyDataSetChanged();
    }

    @NonNull
    @Override
    public PhotoViewHolder onCreateViewHolder(@NonNull ViewGroup parent, int viewType) {
        View view;
        if (this.viewType == VIEW_TYPE_GRID) {
            view = LayoutInflater.from(context).inflate(R.layout.item_photo_grid, parent, false);
        } else {
            view = LayoutInflater.from(context).inflate(R.layout.item_photo_list, parent, false);
        }
        return new PhotoViewHolder(view);
    }

    @Override
    public void onBindViewHolder(@NonNull PhotoViewHolder holder, int position) {
        String gridItem = gridItems.get(position);
        // Tải ảnh vào ImageView, ví dụ sử dụng Glide:
        // Glide.with(context).load(gridItem).into(holder.imageView);

        // Tạm thời, sử dụng placeholder
        holder.imageView.setImageResource(R.drawable.ic_placeholder);
    }

    @Override
    public int getItemCount() {
        return gridItems.size();
    }

    static class PhotoViewHolder extends RecyclerView.ViewHolder {
        ImageView imageView;

        PhotoViewHolder(@NonNull View itemView) {
            super(itemView);
            imageView = itemView.findViewById(R.id.imageView);
        }
    }
}