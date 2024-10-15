package com.example.appchiasecongthucnauan.adapters;

import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.TextView;
import androidx.recyclerview.widget.RecyclerView;
import java.util.ArrayList;
import java.util.Collections;
import java.util.List;
import com.example.appchiasecongthucnauan.R;
import com.example.appchiasecongthucnauan.models.Recipe_1;

public class RecipesAdapter extends RecyclerView.Adapter<RecipesAdapter.RecipeViewHolder> {
    private List<Recipe_1> recipes = new ArrayList<>();

    class RecipeViewHolder extends RecyclerView.ViewHolder {
        TextView titleTextView;
        TextView authorTextView;

        RecipeViewHolder(View itemView) {
            super(itemView);
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
