package com.synrgy.microservices.service;

import com.corundumstudio.socketio.SocketIOServer;
import com.synrgy.microservices.model.MessageData;
import com.synrgy.microservices.service.listener.MessageDataListener;
import jakarta.annotation.PostConstruct;
import org.springframework.stereotype.Service;

@Service
public class ChatService {
    private final SocketIOServer socketIOServer;
    private final MessageDataListener messageDataListener;

    public ChatService(SocketIOServer socketIOServer, MessageDataListener messageDataListener) {
        this.socketIOServer=socketIOServer;
        this.messageDataListener=messageDataListener;
    }

    @PostConstruct
    private void init() {
        socketIOServer.addEventListener("binarfud-product", MessageData.class, messageDataListener);
    }
}
