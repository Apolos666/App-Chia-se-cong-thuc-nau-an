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
            UserViewHolder userHolder = (UserViewHolder) holder;
            User user = (User) items.get(position);

            userHolder.nameTextView.setText(user.getName());
            userHolder.usernameTextView.setText(user.getUsername());

            // Nếu có userImageView, bạn có thể thêm code để load ảnh ở đây
            // Ví dụ: Glide.with(holder.itemView.getContext()).load(user.getImageUrl()).into(userHolder.userImageView);
        } else if (holder instanceof RecipeViewHolder) {
            RecipeViewHolder recipeHolder = (RecipeViewHolder) holder;
            Recipe_1 recipe = (Recipe_1) items.get(position);

            recipeHolder.titleTextView.setText(recipe.getTitle());
            recipeHolder.authorTextView.setText(recipe.getAuthor());

            // Nếu có imageView cho công thức, bạn cũng có thể thêm code để load ảnh ở đây
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

    private boolean isAscending = true;

    public void toggleSortOrder() {
        isAscending = !isAscending;
        Collections.sort(items, (o1, o2) -> {
            if (o1 instanceof User && o2 instanceof User) {
                User u1 = (User) o1;
                User u2 = (User) o2;
                return isAscending ? u1.getName().compareTo(u2.getName()) : u2.getName().compareTo(u1.getName());
            } else if (o1 instanceof Recipe_1 && o2 instanceof Recipe_1) {
                Recipe_1 r1 = (Recipe_1) o1;
                Recipe_1 r2 = (Recipe_1) o2;
                return isAscending ? r1.getTitle().compareTo(r2.getTitle()) : r2.getTitle().compareTo(r1.getTitle());
            }
            return 0;
        });
    }
}