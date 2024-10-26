package com.example.appchiasecongthucnauan.models;

import java.text.ParseException;
import java.text.SimpleDateFormat;
import java.util.Date;
import java.util.Locale;

public class ChatMessage {
    private String message;
    private boolean isSentByUser;
    private String time;

    public ChatMessage(String message, boolean isSentByUser, String timeString) {
        this.message = message;
        this.isSentByUser = isSentByUser;
        this.time = formatTime(timeString);
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

    private String formatTime(String timeString) {
        try {
            SimpleDateFormat inputFormat = new SimpleDateFormat("yyyy-MM-dd'T'HH:mm:ss.SSS'Z'", Locale.getDefault());
            Date date = inputFormat.parse(timeString);
            SimpleDateFormat outputFormat = new SimpleDateFormat("HH:mm", Locale.getDefault());
            return outputFormat.format(date);
        } catch (ParseException e) {
            e.printStackTrace();
            return timeString; // Trả về chuỗi gốc nếu không thể parse
        }
    }
}
