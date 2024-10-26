package com.example.appchiasecongthucnauan.adapters;

import android.content.Context;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ImageView;

import androidx.annotation.NonNull;
import androidx.recyclerview.widget.RecyclerView;

import com.bumptech.glide.Glide;
import com.example.appchiasecongthucnauan.R;

import java.util.List;

public class PhotoAdapter extends RecyclerView.Adapter<PhotoAdapter.PhotoViewHolder> {
    public static final int VIEW_TYPE_GRID = 0;
    public static final int VIEW_TYPE_LIST = 1;

    private Context context;
    private List<String> mediaUrls;
    private int viewType = VIEW_TYPE_GRID;

    public PhotoAdapter(Context context, List<String> mediaUrls) {
        this.context = context;
        this.mediaUrls = mediaUrls;
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
        String mediaUrl = mediaUrls.get(position);
        Glide.with(context)
                .load(mediaUrl)
                .placeholder(R.drawable.ic_placeholder)
                .error(R.drawable.ic_error)
                .into(holder.imageView);
    }

    @Override
    public int getItemCount() {
        return mediaUrls.size();
    }

    static class PhotoViewHolder extends RecyclerView.ViewHolder {
        ImageView imageView;

        PhotoViewHolder(@NonNull View itemView) {
            super(itemView);
            imageView = itemView.findViewById(R.id.imageView);
        }
    }
}
