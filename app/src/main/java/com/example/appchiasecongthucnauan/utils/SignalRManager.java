package com.example.appchiasecongthucnauan.utils;

import android.util.Log;

import com.microsoft.signalr.Action4;
import com.microsoft.signalr.HubConnection;
import com.microsoft.signalr.HubConnectionBuilder;
import com.microsoft.signalr.HubConnectionState;
import java.util.HashMap;
import java.util.Map;
import com.microsoft.signalr.Action1;
import com.microsoft.signalr.Action2;
import com.microsoft.signalr.Action3;

import io.reactivex.rxjava3.core.Single;


public class SignalRManager {
    private static SignalRManager instance;
    private Map<String, HubConnection> hubConnections;
    private static final String BASE_URL = "https://app-chia-se-cong-thuc.azurewebsites.net";

    private SignalRManager() {
        hubConnections = new HashMap<>();
    }

    public static synchronized SignalRManager getInstance() {
        if (instance == null) {
            instance = new SignalRManager();
        }
        return instance;
    }

    public String getBaseUrl() {
        return BASE_URL;
    }

    public void initializeConnection(String hubName, String token) {
        String hubUrl = BASE_URL + "/" + hubName;
        Log.e("SignalR", hubUrl);
        HubConnection hubConnection = HubConnectionBuilder.create(hubUrl)
                .withAccessTokenProvider(Single.just(token))
                .build();
        hubConnections.put(hubName, hubConnection);
    }

    public void joinGroup(String hubName, String methodName, String groupName) {
        HubConnection hubConnection = hubConnections.get(hubName);
        if (hubConnection != null && hubConnection.getConnectionState() == HubConnectionState.CONNECTED) {
            hubConnection.invoke(methodName, groupName);
        }
    }

    public void leaveGroup(String hubName, String methodName, String groupName) {
        HubConnection hubConnection = hubConnections.get(hubName);
        if (hubConnection != null && hubConnection.getConnectionState() == HubConnectionState.CONNECTED) {
            hubConnection.invoke(methodName, groupName);
        }
    }

    public void startConnection(String hubName) {
        HubConnection hubConnection = hubConnections.get(hubName);
        if (hubConnection != null) {
            hubConnection.start().blockingAwait();
        }
    }

    public void stopConnection(String hubName) {
        HubConnection hubConnection = hubConnections.get(hubName);
        if (hubConnection != null) {
            hubConnection.stop();
        }
    }

    public void on(String hubName, String eventName, Action1<String> callback) {
        HubConnection hubConnection = hubConnections.get(hubName);
        if (hubConnection != null) {
            hubConnection.on(eventName, callback, String.class);
        }
    }

    public void on(String hubName, String eventName, Action2<String, String> callback) {
        HubConnection hubConnection = hubConnections.get(hubName);
        if (hubConnection != null) {
            hubConnection.on(eventName, callback, String.class, String.class);
        }
    }

    public void on(String hubName, String eventName, Action3<String, String, String> callback) {
        HubConnection hubConnection = hubConnections.get(hubName);
        if (hubConnection != null) {
            hubConnection.on(eventName, callback, String.class, String.class, String.class);
        }
    }

    public void on(String hubName, String eventName, Action4<String, String, String, String> callback) {
        HubConnection hubConnection = hubConnections.get(hubName);
        if (hubConnection != null) {
            hubConnection.on(eventName, callback, String.class, String.class, String.class, String.class);
        }
    }

    public void stopAllConnections() {
        for (HubConnection connection : hubConnections.values()) {
            connection.stop();
        }
        hubConnections.clear();
    }
}
