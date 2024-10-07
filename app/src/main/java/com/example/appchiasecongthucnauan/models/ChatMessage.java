package com.example.appchiasecongthucnauan.models;

public class ChatMessage {
    private String message;
    private boolean isSentByUser;
    private String time;

    public ChatMessage(String message, boolean isSentByUser, String time) {
        this.message = message;
        this.isSentByUser = isSentByUser;
        this.time = time;
    }

    public String getMessage() {
        return message;
    }

    public boolean isSentByUser() {
        return isSentByUser;
    }

    public String getTime() {
        return time;
    }
}

