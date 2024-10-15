package com.example.appchiasecongthucnauan.adapters;

import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ImageView;
import android.widget.TextView;
import androidx.annotation.NonNull;
import androidx.recyclerview.widget.RecyclerView;
import com.example.appchiasecongthucnauan.R;
import com.example.appchiasecongthucnauan.models.RecentItem;

import java.util.List;
public class RecentAdapter extends RecyclerView.Adapter<RecentAdapter.ViewHolder> {
    private List<RecentItem> items;

    public RecentAdapter(List<RecentItem> items) {
        this.items = items;
    }

    @NonNull
    @Override
    public ViewHolder onCreateViewHolder(@NonNull ViewGroup parent, int viewType) {
        View view = LayoutInflater.from(parent.getContext()).inflate(R.layout.item_recent, parent, false);
        return new ViewHolder(view);
    }

    @Override
    public void onBindViewHolder(@NonNull ViewHolder holder, int position) {
        RecentItem item = items.get(position);
        holder.titleTextView.setText(item.getTitle());
        holder.authorTextView.setText(item.getAuthor());
        holder.timeTextView.setText(item.getTimeAgo());
        // Load image using Glide or Picasso
        // Glide.with(holder.imageView.getContext()).load(item.getImageUrl()).into(holder.imageView);
    }

    @Override
    public int getItemCount() {
        return items.size();
    }

    static class ViewHolder extends RecyclerView.ViewHolder {
        ImageView imageView;
        TextView titleTextView;
        TextView authorTextView;
        TextView timeTextView;

        ViewHolder(View itemView) {
            super(itemView);
            imageView = itemView.findViewById(R.id.imageView);
            titleTextView = itemView.findViewById(R.id.titleTextView);
            authorTextView = itemView.findViewById(R.id.authorTextView);
            timeTextView = itemView.findViewById(R.id.timeTextView);
        }
    }
}