package com.example.appchiasecongthucnauan.activities;

import android.content.SharedPreferences;
import android.os.Bundle;
import android.util.Log;
import android.widget.EditText;
import android.widget.ImageButton;
import android.widget.TextView;
import android.widget.Toast;

import androidx.appcompat.app.AppCompatActivity;
import androidx.recyclerview.widget.LinearLayoutManager;
import androidx.recyclerview.widget.RecyclerView;

import com.example.appchiasecongthucnauan.R;
import com.example.appchiasecongthucnauan.adapters.ChatAdapter;
import com.example.appchiasecongthucnauan.apis.ApiService;
import com.example.appchiasecongthucnauan.models.ChatMessage;
import com.example.appchiasecongthucnauan.models.SendMessageRequest;
import com.example.appchiasecongthucnauan.models.MessageDto;
import com.example.appchiasecongthucnauan.models.ConversationDto;
import com.example.appchiasecongthucnauan.utils.SignalRManager;

import java.util.ArrayList;
import java.util.List;
import java.util.concurrent.TimeUnit;

import okhttp3.OkHttpClient;
import retrofit2.Call;
import retrofit2.Callback;
import retrofit2.Response;
import retrofit2.Retrofit;
import retrofit2.converter.gson.GsonConverterFactory;

public class ChatActivity extends AppCompatActivity {

    private RecyclerView chatRecyclerView;
    private ChatAdapter chatAdapter;
    private List<ChatMessage> messageList;
    private EditText messageInput;
    private ImageButton sendButton;
    private ImageButton backButton;
    private ApiService apiService;
    private SignalRManager signalRManager;
    private String conversationId;
    private String otherUserId;
    private String currentUserId;
    private TextView userName, userSurname;

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
        backButton = findViewById(R.id.btnBack);

        userName = findViewById(R.id.user_name);
        userSurname = findViewById(R.id.user_sur_name);

        // Set up back button functionality
        backButton.setOnClickListener(v -> finish());

        setupRetrofit();
        setupSignalR();
        loadCurrentUser();

        String recipientId = getIntent().getStringExtra("RECIPIENT_ID");
        String currentUserId = getIntent().getStringExtra("CURRENT_USER_ID");

        if (recipientId != null && currentUserId != null) {
            startConversation(recipientId);
            loadUserInfo();
        } else {
            // Xử lý trường hợp không có đủ thông tin
            Toast.makeText(this, "Không đủ thông tin để bắt đầu cuộc trò chuyện", Toast.LENGTH_SHORT).show();
            finish();
        }

        sendButton.setOnClickListener(v -> sendMessage());
    }

    private void setupRetrofit() {
        OkHttpClient client = new OkHttpClient.Builder()
                .connectTimeout(60, TimeUnit.SECONDS)
                .readTimeout(60, TimeUnit.SECONDS)
                .writeTimeout(60, TimeUnit.SECONDS)
                .build();

        Retrofit retrofit = new Retrofit.Builder()
                .baseUrl("http://10.0.2.2:5076/")
                .addConverterFactory(GsonConverterFactory.create())
                .client(client)
                .build();

        apiService = retrofit.create(ApiService.class);
    }

    private void setupSignalR() {
        signalRManager = SignalRManager.getInstance();
        signalRManager.setBaseUrl("http://10.0.2.2:5076");
        String token = getSharedPreferences("MyAppPrefs", MODE_PRIVATE).getString("token", "");
        signalRManager.initializeConnection("hub/chat", token);
        signalRManager.startConnection("hub/chat");

        signalRManager.on("hub/chat", "ReceiveMessage", (senderId, senderName, message, sentAt) -> {
            runOnUiThread(() -> {
                messageList.add(new ChatMessage(message, senderId.equals(currentUserId), sentAt));
                chatAdapter.notifyItemInserted(messageList.size() - 1);
                chatRecyclerView.scrollToPosition(messageList.size() - 1);
            });
        });
    }

    private void loadCurrentUser() {
        SharedPreferences sharedPreferences = getSharedPreferences("MyAppPrefs", MODE_PRIVATE);
        currentUserId = sharedPreferences.getString("userId", "");
    }

    private void startConversation(String recipientId) {
        String token = getSharedPreferences("MyAppPrefs", MODE_PRIVATE).getString("token", "");
        apiService.startConversation("Bearer " + token, recipientId).enqueue(new Callback<String>() {
            @Override
            public void onResponse(Call<String> call, Response<String> response) {
                if (response.isSuccessful() && response.body() != null) {
                    conversationId = response.body();
                    signalRManager.joinGroup("hub/chat", "JoinConversation", conversationId);
                    loadMessages(conversationId);
                }
            }

            @Override
            public void onFailure(Call<String> call, Throwable t) {
                // Xử lý lỗi
                Toast.makeText(ChatActivity.this, "Không thể bắt đầu cuộc trò chuyện", Toast.LENGTH_SHORT).show();
            }
        });
    }

    private void loadUserInfo() {
        userName.setText(getIntent().getStringExtra("RECIPIENT_NAME"));
        userSurname.setText("@" + getIntent().getStringExtra("RECIPIENT_NAME"));
    }

    private void loadMessages(String conversationId) {
        String token = getSharedPreferences("MyAppPrefs", MODE_PRIVATE).getString("token", "");
        apiService.getMessages("Bearer " + token, conversationId).enqueue(new Callback<List<MessageDto>>() {
            @Override
            public void onResponse(Call<List<MessageDto>> call, Response<List<MessageDto>> response) {
                if (response.isSuccessful() && response.body() != null) {
                    List<MessageDto> messages = response.body();
                    chatAdapter.setMessages(messages);
                    chatRecyclerView.scrollToPosition(chatAdapter.getItemCount() - 1);
                }
            }

            @Override
            public void onFailure(Call<List<MessageDto>> call, Throwable t) {
                // Xử lý lỗi
                Toast.makeText(ChatActivity.this, "Không thể tải tin nhắn", Toast.LENGTH_SHORT).show();
            }
        });
    }

    private void sendMessage() {
        String messageText = messageInput.getText().toString().trim();
        if (!messageText.isEmpty()) {
            String token = getSharedPreferences("MyAppPrefs", MODE_PRIVATE).getString("token", "");
            String userName = getSharedPreferences("MyAppPrefs", MODE_PRIVATE).getString("userName", "");
            SendMessageRequest request = new SendMessageRequest(userName, conversationId, messageText);
            apiService.sendMessage("Bearer " + token, request).enqueue(new Callback<Void>() {
                @Override
                public void onResponse(Call<Void> call, Response<Void> response) {
                    if (response.isSuccessful()) {
                        messageInput.setText("");
                    }
                }

                @Override
                public void onFailure(Call<Void> call, Throwable t) {
                    // Xử lý lỗi
                }
            });
        }
    }

    @Override
    protected void onDestroy() {
        super.onDestroy();
        signalRManager.leaveGroup("hub/chat", "LeaveConversation", conversationId);
        signalRManager.stopConnection("hub/chat");
    }
}
