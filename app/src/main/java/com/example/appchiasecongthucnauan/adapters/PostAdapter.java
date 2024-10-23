package com.example.appchiasecongthucnauan.adapters;

import android.content.Context;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.Button;
import android.widget.ImageView;
import android.widget.TextView;
import android.widget.VideoView;
import android.net.Uri;
import android.media.MediaPlayer;

import androidx.annotation.NonNull;
import androidx.recyclerview.widget.RecyclerView;

import com.bumptech.glide.Glide;
import com.example.appchiasecongthucnauan.R;
import com.example.appchiasecongthucnauan.models.Post;

import java.util.List;

public class PostAdapter extends RecyclerView.Adapter<PostAdapter.PostViewHolder> {

    private Context context;
    private List<Post> postList;
    private OnPostClickListener onPostClickListener;

    public interface OnPostClickListener {
        void onPostClick(Post post);
    }

    public PostAdapter(Context context, List<Post> postList, OnPostClickListener onPostClickListener) {
        this.context = context;
        this.postList = postList;
        this.onPostClickListener = onPostClickListener;
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
        holder.chefName.setText(post.getChefName());
        holder.likeCount.setText(String.valueOf(post.getLikeCount()));
        holder.commentCount.setText(String.valueOf(post.getCommentCount()));

        String mediaUrl = post.getMediaUrl();
        if (mediaUrl != null && !mediaUrl.isEmpty()) {
            if (mediaUrl.endsWith(".mp4")) {
                holder.recipeImage.setVisibility(View.GONE);
                holder.recipeVideo.setVisibility(View.VISIBLE);
                Uri videoUri = Uri.parse(mediaUrl);
                holder.recipeVideo.setVideoURI(videoUri);
                holder.recipeVideo.setOnPreparedListener(new MediaPlayer.OnPreparedListener() {
                    @Override
                    public void onPrepared(MediaPlayer mp) {
                        mp.setLooping(true);
                        holder.recipeVideo.start();
                    }
                });
            } else {
                holder.recipeImage.setVisibility(View.VISIBLE);
                holder.recipeVideo.setVisibility(View.GONE);
                Glide.with(context)
                        .load(mediaUrl)
                        .into(holder.recipeImage);
            }
        } else {
            holder.recipeImage.setVisibility(View.GONE);
            holder.recipeVideo.setVisibility(View.GONE);
        }

        holder.viewRecipeBtn.setOnClickListener(v -> {
            if (onPostClickListener != null) {
                onPostClickListener.onPostClick(post);
            }
        });
    }

    @Override
    public int getItemCount() {
        return postList.size();
    }

    public static class PostViewHolder extends RecyclerView.ViewHolder {

        ImageView recipeImage;
        VideoView recipeVideo;
        TextView recipeName, chefName, likeCount, commentCount;
        Button viewRecipeBtn;

        public PostViewHolder(@NonNull View itemView) {
            super(itemView);
            recipeImage = itemView.findViewById(R.id.recipe_image);
            recipeVideo = itemView.findViewById(R.id.recipe_video);
            recipeName = itemView.findViewById(R.id.recipe_name);
            chefName = itemView.findViewById(R.id.chef_name);
            likeCount = itemView.findViewById(R.id.like_count);
            commentCount = itemView.findViewById(R.id.comment_count);
            viewRecipeBtn = itemView.findViewById(R.id.view_recipe_btn);
        }
    }
}
