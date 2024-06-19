package com.synrgy.microservices.service;

import org.springframework.stereotype.Service;

import com.corundumstudio.socketio.SocketIOServer;
import com.synrgy.microservices.model.MessageData;
import com.synrgy.microservices.service.listener.MessageDataListener;

import jakarta.annotation.PostConstruct;

@Service
public class PromoNotifService {
    private final SocketIOServer socketIOServer;
    private final MessageDataListener messageDataListener;

    public PromoNotifService(SocketIOServer socketIOServer, MessageDataListener messageDataListener) {
        this.socketIOServer=socketIOServer;
        this.messageDataListener=messageDataListener;
    }

    @PostConstruct
    private void init() {
        socketIOServer.addEventListener("binarfud-product", MessageData.class, messageDataListener);
    }
}
