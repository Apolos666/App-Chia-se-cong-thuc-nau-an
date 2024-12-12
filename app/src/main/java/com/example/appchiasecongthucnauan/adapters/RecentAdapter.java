package com.example.appchiasecongthucnauan.adapters;

import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ImageView;
import android.widget.TextView;

import androidx.annotation.NonNull;
import androidx.recyclerview.widget.RecyclerView;

import com.bumptech.glide.Glide;
import com.bumptech.glide.load.resource.bitmap.RoundedCorners;
import com.example.appchiasecongthucnauan.R;
import com.example.appchiasecongthucnauan.activities.RecipeDetailActivity;
import com.example.appchiasecongthucnauan.models.explore.RecentRecipeDto;
import com.example.appchiasecongthucnauan.utils.TimeUtils;

import java.util.List;

public class RecentAdapter extends RecyclerView.Adapter<RecentAdapter.ViewHolder> {
    private List<RecentRecipeDto> recipes;

    public RecentAdapter(List<RecentRecipeDto> recipes) {
        this.recipes = recipes;
    }

    @NonNull
    @Override
    public ViewHolder onCreateViewHolder(@NonNull ViewGroup parent, int viewType) {
        View view = LayoutInflater.from(parent.getContext()).inflate(R.layout.item_recent, parent, false);
        return new ViewHolder(view);
    }

    @Override
    public void onBindViewHolder(@NonNull ViewHolder holder, int position) {
        RecentRecipeDto recipe = recipes.get(position);
        holder.titleTextView.setText(recipe.getTitle());
        holder.authorTextView.setText("Bởi:" + recipe.getChefName());
        holder.timeTextView.setText(TimeUtils.getTimeAgo(recipe.getCreatedAt()));

        if (recipe.getImageUrl() != null) {
            Glide.with(holder.imageView.getContext())
                    .load(recipe.getImageUrl())
                    .transform(new RoundedCorners(20))
                    .placeholder(R.drawable.placeholder_image)
                    .into(holder.imageView);
        }
        holder.itemView.setOnClickListener(v -> {
            // Navigate to recipe detail
            RecipeDetailActivity.start(
                    holder.itemView.getContext(),
                    recipe.getId()
            );
        });
    }

    @Override
    public int getItemCount() {
        return recipes.size();
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