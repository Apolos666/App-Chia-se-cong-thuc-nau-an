package com.example.appchiasecongthucnauan.models;

public class SendMessageRequest {
    private String senderUserName;
    private String conversationId;
    private String content;

    public SendMessageRequest(String senderUserName, String conversationId, String content) {
        this.senderUserName = senderUserName;
        this.conversationId = conversationId;
        this.content = content;
    }

    public String getSenderUserName() {
        return senderUserName;
    }

    public void setSenderUserName(String senderUserName) {
        this.senderUserName = senderUserName;
    }

    public String getConversationId() {
        return conversationId;
    }

    public void setConversationId(String conversationId) {
        this.conversationId = conversationId;
    }

    public String getContent() {
        return content;
    }

    public void setContent(String content) {
        this.content = content;
    }
}
