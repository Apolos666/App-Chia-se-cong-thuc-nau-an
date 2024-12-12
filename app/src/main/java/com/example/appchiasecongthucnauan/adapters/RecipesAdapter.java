package com.example.appchiasecongthucnauan.adapters;

import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ImageView;
import android.widget.TextView;
import androidx.recyclerview.widget.RecyclerView;
import com.bumptech.glide.Glide;
import java.util.ArrayList;
import java.util.Collections;
import java.util.List;
import com.example.appchiasecongthucnauan.R;
import com.example.appchiasecongthucnauan.activities.RecipeDetailActivity;
import com.example.appchiasecongthucnauan.models.Recipe_1;

public class RecipesAdapter extends RecyclerView.Adapter<RecipesAdapter.RecipeViewHolder> {
    private List<Recipe_1> recipes = new ArrayList<>();

    class RecipeViewHolder extends RecyclerView.ViewHolder {
        ImageView thumbnailImageView;
        TextView titleTextView;
        TextView authorTextView;

        RecipeViewHolder(View itemView) {
            super(itemView);
            thumbnailImageView = itemView.findViewById(R.id.thumbnailImageView);
            titleTextView = itemView.findViewById(R.id.titleTextView);
            authorTextView = itemView.findViewById(R.id.authorTextView);
        }
    }

    @Override
    public RecipeViewHolder onCreateViewHolder(ViewGroup parent, int viewType) {
        View view = LayoutInflater.from(parent.getContext()).inflate(R.layout.item_recipe, parent, false);
        return new RecipeViewHolder(view);
    }

    @Override
    public void onBindViewHolder(RecipeViewHolder holder, int position) {
        Recipe_1 recipe = recipes.get(position);
        holder.titleTextView.setText(recipe.getTitle());
        holder.authorTextView.setText(recipe.getAuthor());

        // Load thumbnail using Glide
        if (recipe.getImageUrl()!= null && !recipe.getImageUrl().isEmpty()) {
            Glide.with(holder.thumbnailImageView.getContext())
                .load(recipe.getImageUrl())
                .placeholder(R.drawable.placeholder_image)
                .error(R.drawable.placeholder_image)
                .centerCrop()
                .into(holder.thumbnailImageView);
        } else {
            holder.thumbnailImageView.setImageResource(R.drawable.placeholder_image);
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

    public void setRecipes(List<Recipe_1> newRecipes) {
        recipes = newRecipes;
        notifyDataSetChanged();
    }

    private boolean isAscending = true;

    public void toggleSortOrder() {
        isAscending = !isAscending;
        Collections.sort(recipes, (r1, r2) ->
                isAscending ? r1.getTitle().compareTo(r2.getTitle()) : r2.getTitle().compareTo(r1.getTitle())
        );
    }
}
