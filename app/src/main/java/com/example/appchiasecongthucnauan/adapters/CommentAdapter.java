package com.example.appchiasecongthucnauan.adapters;

import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ImageView;
import android.widget.TextView;

import androidx.annotation.NonNull;
import androidx.recyclerview.widget.RecyclerView;

import com.example.appchiasecongthucnauan.R;
import com.example.appchiasecongthucnauan.models.Comment;

import java.util.List;

public class CommentAdapter extends RecyclerView.Adapter<CommentAdapter.CommentViewHolder> {
    private List<Comment> comments;
    private OnCommentAvatarClickListener avatarClickListener;

    public interface OnCommentAvatarClickListener {
        void onAvatarClick(String userId);
    }

    public CommentAdapter(List<Comment> comments, OnCommentAvatarClickListener listener) {
        this.comments = comments;
        this.avatarClickListener = listener;
    }

    @NonNull
    @Override
    public CommentViewHolder onCreateViewHolder(@NonNull ViewGroup parent, int viewType) {
        View view = LayoutInflater.from(parent.getContext()).inflate(R.layout.item_comment, parent, false);
        return new CommentViewHolder(view);
    }

    @Override
    public void onBindViewHolder(@NonNull CommentViewHolder holder, int position) {
        Comment comment = comments.get(position);
        holder.bind(comment, avatarClickListener);
    }

    @Override
    public int getItemCount() {
        return comments.size();
    }

    public void addComment(Comment comment) {
        comments.add(comment);
        notifyItemInserted(comments.size() - 1);
    }

    public void setComments(List<Comment> newComments) {
        this.comments = newComments;
        notifyDataSetChanged();
    }

    static class CommentViewHolder extends RecyclerView.ViewHolder {
        private ImageView userAvatar;
        private TextView userNameText;
        private TextView contentText;

        CommentViewHolder(@NonNull View itemView) {
            super(itemView);
            userAvatar = itemView.findViewById(R.id.commentUserAvatar);
            userNameText = itemView.findViewById(R.id.commentUserName);
            contentText = itemView.findViewById(R.id.commentContent);
        }

        void bind(Comment comment, OnCommentAvatarClickListener listener) {
            userNameText.setText(comment.getUserName());
            contentText.setText(comment.getContent());

            userAvatar.setOnClickListener(v -> {
                if (listener != null) {
                    listener.onAvatarClick(comment.getUserId());
                }
            });
        }
    }
}
