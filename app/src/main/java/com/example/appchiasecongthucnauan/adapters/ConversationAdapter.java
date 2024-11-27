package com.example.appchiasecongthucnauan.adapters;

import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.TextView;
import androidx.annotation.NonNull;
import androidx.recyclerview.widget.RecyclerView;
import com.example.appchiasecongthucnauan.R;
import com.example.appchiasecongthucnauan.models.ConversationDto;
import java.text.SimpleDateFormat;
import java.util.ArrayList;
import java.util.List;
import java.util.Locale;

public class ConversationAdapter extends RecyclerView.Adapter<ConversationAdapter.ViewHolder> {
    private List<ConversationDto> conversations; // Lưu danh sách gốc không thay đổi
    private List<ConversationDto> conversationsFiltered; // Lưu danh sách đã được lọc, dùng để hiển thị
    private OnConversationClickListener listener;

    @FunctionalInterface
    public interface OnConversationClickListener {
        void onConversationClick(ConversationDto conversation);
    }

    public ConversationAdapter(List<ConversationDto> conversations, OnConversationClickListener listener) {
        this.conversations = conversations;
        this.conversationsFiltered = new ArrayList<>(conversations);
        this.listener = listener;
    }

    @NonNull
    @Override
    public ViewHolder onCreateViewHolder(@NonNull ViewGroup parent, int viewType) {
        View view = LayoutInflater.from(parent.getContext())
                .inflate(R.layout.item_conversation, parent, false);
        return new ViewHolder(view);
    }

    @Override
    public void onBindViewHolder(@NonNull ViewHolder holder, int position) {
        ConversationDto conversation = conversationsFiltered.get(position);
        holder.userName.setText(conversation.getOtherUserName());
        if (conversation.getLastMessage() != null) {
            holder.lastMessage.setText(conversation.getLastMessage().getContent());
            SimpleDateFormat sdf = new SimpleDateFormat("HH:mm", Locale.getDefault());
            holder.messageTime.setText(sdf.format(conversation.getLastMessage().getSentAt()));
        }
        holder.itemView.setOnClickListener(v -> listener.onConversationClick(conversation));
    }

    @Override
    public int getItemCount() {
        return conversationsFiltered.size();
    }

    public void setConversations(List<ConversationDto> conversations) {
        this.conversations = conversations;
        this.conversationsFiltered = new ArrayList<>(conversations);
        notifyDataSetChanged();
    }

    public void filter(String query) {
        conversationsFiltered.clear();
        if (query.isEmpty()) {
            conversationsFiltered.addAll(conversations);
        } else {
            String lowerCaseQuery = query.toLowerCase().trim();
            for (ConversationDto conversation : conversations) {
                if (conversation.getOtherUserName().toLowerCase().contains(lowerCaseQuery)) {
                    conversationsFiltered.add(conversation);
                }
            }
        }
        notifyDataSetChanged(); // Cập nhật RecyclerView
    }

    static class ViewHolder extends RecyclerView.ViewHolder {
        TextView userName, lastMessage, messageTime;

        ViewHolder(View itemView) {
            super(itemView);
            userName = itemView.findViewById(R.id.userName);
            lastMessage = itemView.findViewById(R.id.lastMessage);
            messageTime = itemView.findViewById(R.id.messageTime);
        }
    }
}