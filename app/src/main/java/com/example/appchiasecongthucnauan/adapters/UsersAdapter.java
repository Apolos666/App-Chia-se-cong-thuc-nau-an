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

public class UsersAdapter extends RecyclerView.Adapter<UsersAdapter.UserViewHolder> {
    private List<User> users = new ArrayList<>();

    class UserViewHolder extends RecyclerView.ViewHolder {
        TextView nameTextView;
        TextView usernameTextView;

        UserViewHolder(View itemView) {
            super(itemView);
            nameTextView = itemView.findViewById(R.id.nameTextView);
            usernameTextView = itemView.findViewById(R.id.usernameTextView);
        }
    }

    @Override
    public UserViewHolder onCreateViewHolder(ViewGroup parent, int viewType) {
        View view = LayoutInflater.from(parent.getContext()).inflate(R.layout.item_user, parent, false);
        return new UserViewHolder(view);
    }

    @Override
    public void onBindViewHolder(UserViewHolder holder, int position) {
        User user = users.get(position);
        holder.nameTextView.setText(user.getName());
        holder.usernameTextView.setText(user.getUsername());
    }

    @Override
    public int getItemCount() {
        return users.size();
    }

    public void setUsers(List<User> newUsers) {
        users = newUsers;
        notifyDataSetChanged();
    }
}
