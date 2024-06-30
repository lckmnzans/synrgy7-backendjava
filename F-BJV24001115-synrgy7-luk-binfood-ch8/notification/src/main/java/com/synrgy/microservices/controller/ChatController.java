package com.synrgy.microservices.controller;

import com.corundumstudio.socketio.SocketIOServer;
import com.corundumstudio.socketio.listener.ConnectListener;
import com.corundumstudio.socketio.listener.DisconnectListener;
import org.springframework.stereotype.Component;

@Component
public class ChatController {
    private final SocketIOServer socketIOServer;

    public ChatController(SocketIOServer socketIOServer) {
        this.socketIOServer = socketIOServer;
        this.socketIOServer.addConnectListener(onUserConnected);
        this.socketIOServer.addDisconnectListener(onUserDisconnected);
    }

    private ConnectListener onUserConnected = socketIOClient -> System.out.println("A socketIOClient is connected");
    private DisconnectListener onUserDisconnected = socketIOClient -> System.out.println("A socketIOClient is disconnected"); 
}
