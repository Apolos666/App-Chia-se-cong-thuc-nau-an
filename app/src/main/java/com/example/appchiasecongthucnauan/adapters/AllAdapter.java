package com.example.appchiasecongthucnauan.adapters;

import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.TextView;
import androidx.recyclerview.widget.RecyclerView;
import java.util.ArrayList;
import java.util.List;
import com.example.appchiasecongthucnauan.R;
import com.example.appchiasecongthucnauan.models.User;

public class AllAdapter extends RecyclerView.Adapter<RecyclerView.ViewHolder> {
    private List<Object> items = new ArrayList<>();

    // ViewHolder for user items
    class UserViewHolder extends RecyclerView.ViewHolder {
        TextView nameTextView;
        TextView usernameTextView;

        UserViewHolder(View itemView) {
            super(itemView);
            nameTextView = itemView.findViewById(R.id.nameTextView);
            usernameTextView = itemView.findViewById(R.id.usernameTextView);
        }
    }

    // ViewHolder for recipe items
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
    public RecyclerView.ViewHolder onCreateViewHolder(ViewGroup parent, int viewType) {
        if (viewType == 0) {
            View view = LayoutInflater.from(parent.getContext()).inflate(R.layout.item_user, parent, false);
            return new UserViewHolder(view);
        } else {
            View view = LayoutInflater.from(parent.getContext()).inflate(R.layout.item_recipe, parent, false);
            return new RecipeViewHolder(view);
        }
    }

    @Override
    public void onBindViewHolder(RecyclerView.ViewHolder holder, int position) {
        if (holder instanceof UserViewHolder) {
            // Bind user data
        } else if (holder instanceof RecipeViewHolder) {
            // Bind recipe data
        }
    }

    @Override
    public int getItemCount() {
        return items.size();
    }

    @Override
    public int getItemViewType(int position) {
        return items.get(position) instanceof User ? 0 : 1;
    }

    public void setItems(List<Object> newItems) {
        items = newItems;
        notifyDataSetChanged();
    }
}