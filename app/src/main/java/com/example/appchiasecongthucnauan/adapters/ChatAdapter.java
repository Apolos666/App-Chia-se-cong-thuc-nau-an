package com.example.appchiasecongthucnauan.adapters;

import android.content.Context;
import android.content.SharedPreferences;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.TextView;

import androidx.annotation.NonNull;
import androidx.recyclerview.widget.RecyclerView;

import com.example.appchiasecongthucnauan.R;
import com.example.appchiasecongthucnauan.models.ChatMessage;
import com.example.appchiasecongthucnauan.models.MessageDto;

import java.util.List;
import java.text.SimpleDateFormat;
import java.util.Date;
import java.util.Locale;

public class ChatAdapter extends RecyclerView.Adapter<ChatAdapter.ChatViewHolder> {

    private Context context;
    private List<ChatMessage> messageList;

    public ChatAdapter(Context context, List<ChatMessage> messageList) {
        this.context = context;
        this.messageList = messageList;
    }

    @NonNull
    @Override
    public ChatViewHolder onCreateViewHolder(@NonNull ViewGroup parent, int viewType) {
        View view;
        if (viewType == 1) {
            view = LayoutInflater.from(context).inflate(R.layout.item_message_sent, parent, false);
        } else {
            view = LayoutInflater.from(context).inflate(R.layout.item_message_received, parent, false);
        }
        return new ChatViewHolder(view);
    }

    @Override
    public void onBindViewHolder(@NonNull ChatViewHolder holder, int position) {
        ChatMessage message = messageList.get(position);
        holder.messageText.setText(message.getMessage());
        holder.timeText.setText(message.getTime());
    }

    @Override
    public int getItemViewType(int position) {
        if (messageList.get(position).isSentByUser()) {
            return 1; // Sent message
        } else {
            return 0; // Received message
        }
    }

    @Override
    public int getItemCount() {
        return messageList.size();
    }

    public static class ChatViewHolder extends RecyclerView.ViewHolder {
        TextView messageText, timeText;

        public ChatViewHolder(@NonNull View itemView) {
            super(itemView);
            messageText = itemView.findViewById(R.id.message_text);
            timeText = itemView.findViewById(R.id.time_text);
        }
    }

    public void addMessage(MessageDto message) {
        messageList.add(new ChatMessage(
                message.getContent(),
                message.getSenderId().equals(getCurrentUserId()),
                formatDate(message.getSentAt())));
        notifyItemInserted(messageList.size() - 1);
    }

    public void setMessages(List<MessageDto> messages) {
        messageList.clear();
        for (MessageDto message : messages) {
            messageList.add(new ChatMessage(
                    message.getContent(),
                    message.getSenderId().equals(getCurrentUserId()),
                    formatDate(message.getSentAt())));
        }
        notifyDataSetChanged();
    }

    private String getCurrentUserId() {
        SharedPreferences sharedPreferences = context.getSharedPreferences("MyAppPrefs", Context.MODE_PRIVATE);
        return sharedPreferences.getString("userId", "");
    }

    private String formatDate(Date date) {
        SimpleDateFormat sdf = new SimpleDateFormat("HH:mm", Locale.getDefault());
        return sdf.format(date);
    }
}
