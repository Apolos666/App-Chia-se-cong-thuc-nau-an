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
import com.example.appchiasecongthucnauan.models.explore.TrendingRecipeDto;

import java.util.List;

public class TrendingAdapter extends RecyclerView.Adapter<TrendingAdapter.ViewHolder> {
    private List<TrendingRecipeDto> recipes;

    public TrendingAdapter(List<TrendingRecipeDto> recipes) {
        this.recipes = recipes;
    }

    @NonNull
    @Override
    public ViewHolder onCreateViewHolder(@NonNull ViewGroup parent, int viewType) {
        View view = LayoutInflater.from(parent.getContext()).inflate(R.layout.item_trending, parent, false);
        return new ViewHolder(view);
    }

    @Override
    public void onBindViewHolder(@NonNull ViewHolder holder, int position) {
        TrendingRecipeDto recipe = recipes.get(position);
        holder.titleTextView.setText(recipe.getTitle());
        holder.authorTextView.setText("Bởi: "+recipe.getChefName());
        holder.trendingTextView.setText(recipe.getLikesCount() + " lượt thích");
        
        // Load ảnh sử dụng Glide
        if (recipe.getImageUrl() != null) {
            Glide.with(holder.imageView.getContext())
                .load(recipe.getImageUrl())
                .placeholder(R.drawable.placeholder_image)
                .into(holder.imageView);
        }
    }

    @Override
    public int getItemCount() {
        return recipes.size();
    }

    static class ViewHolder extends RecyclerView.ViewHolder {
        ImageView imageView;
        TextView titleTextView;
        TextView authorTextView;
        TextView trendingTextView;

        ViewHolder(View itemView) {
            super(itemView);
            imageView = itemView.findViewById(R.id.imageView);
            titleTextView = itemView.findViewById(R.id.titleTextView);
            authorTextView = itemView.findViewById(R.id.authorTextView);
            trendingTextView = itemView.findViewById(R.id.trendingTextView);
        }
    }
}
