package com.example.appchiasecongthucnauan.adapters;

import android.content.Context;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.Button;
import android.widget.ImageView;
import android.widget.TextView;

import androidx.annotation.NonNull;
import androidx.recyclerview.widget.RecyclerView;

import com.example.appchiasecongthucnauan.R;
import com.example.appchiasecongthucnauan.models.Post;

import java.util.List;

public class PostAdapter extends RecyclerView.Adapter<PostAdapter.PostViewHolder> {

    private Context context;
    private List<Post> postList;

    public PostAdapter(Context context, List<Post> postList) {
        this.context = context;
        this.postList = postList;
    }

    @NonNull
    @Override
    public PostViewHolder onCreateViewHolder(@NonNull ViewGroup parent, int viewType) {
        View view = LayoutInflater.from(context).inflate(R.layout.item_post, parent, false);
        return new PostViewHolder(view);
    }

    @Override
    public void onBindViewHolder(@NonNull PostViewHolder holder, int position) {
        Post post = postList.get(position);
        holder.recipeName.setText(post.getRecipeName());
        holder.chefName.setText("By " + post.getChefName());
        holder.likeCount.setText(String.valueOf(post.getLikeCount()));
        holder.commentCount.setText(String.valueOf(post.getCommentCount()));

        // You can add image loading logic here if you have images from the internet.
        // You can add image loading logic here if you have images from the internet.
        // You can add image loading logic here if you have images from the internet.

    }

    @Override
    public int getItemCount() {
        return postList.size();
    }

    public static class PostViewHolder extends RecyclerView.ViewHolder {

        ImageView recipeImage;
        TextView recipeName, chefName, likeCount, commentCount;
        Button viewRecipeBtn;

        public PostViewHolder(@NonNull View itemView) {
            super(itemView);
            recipeImage = itemView.findViewById(R.id.recipe_image);
            recipeName = itemView.findViewById(R.id.recipe_name);
            chefName = itemView.findViewById(R.id.chef_name);
            likeCount = itemView.findViewById(R.id.like_count);
            commentCount = itemView.findViewById(R.id.comment_count);
            viewRecipeBtn = itemView.findViewById(R.id.view_recipe_btn);
        }
    }
}

