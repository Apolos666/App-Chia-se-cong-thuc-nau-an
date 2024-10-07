package com.example.appchiasecongthucnauan.activities;

import android.os.Bundle;
import android.view.View;
import android.widget.EditText;
import android.widget.ImageButton;

import androidx.appcompat.app.AppCompatActivity;
import androidx.recyclerview.widget.LinearLayoutManager;
import androidx.recyclerview.widget.RecyclerView;

import com.example.appchiasecongthucnauan.R;
import com.example.appchiasecongthucnauan.adaptors.ChatAdapter;
import com.example.appchiasecongthucnauan.models.ChatMessage;

import java.util.ArrayList;
import java.util.List;

public class ChatActivity extends AppCompatActivity {

    private RecyclerView chatRecyclerView;
    private ChatAdapter chatAdapter;
    private List<ChatMessage> messageList;
    private EditText messageInput;
    private ImageButton sendButton;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_chat);

        messageList = new ArrayList<>();
        chatRecyclerView = findViewById(R.id.chat_recycler_view);
        chatRecyclerView.setLayoutManager(new LinearLayoutManager(this));
        chatAdapter = new ChatAdapter(this, messageList);
        chatRecyclerView.setAdapter(chatAdapter);

        messageInput = findViewById(R.id.message_input);
        sendButton = findViewById(R.id.send_button);

        // Example messages
        messageList.add(new ChatMessage("Hey! I loved your latest recipe!", false, "10:30 AM"));
        messageList.add(new ChatMessage("Thanks! Did you try making it?", true, "10:32 AM"));

        sendButton.setOnClickListener(v -> {
            String messageText = messageInput.getText().toString().trim();
            if (!messageText.isEmpty()) {
                messageList.add(new ChatMessage(messageText, true, "10:35 AM"));
                chatAdapter.notifyItemInserted(messageList.size() - 1);
                messageInput.setText("");
                chatRecyclerView.scrollToPosition(messageList.size() - 1);
            }
        });
    }
}

