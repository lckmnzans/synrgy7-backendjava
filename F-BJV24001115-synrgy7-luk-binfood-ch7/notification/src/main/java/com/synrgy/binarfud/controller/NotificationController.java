package com.synrgy.binarfud.controller;

import org.springframework.stereotype.Component;

import com.corundumstudio.socketio.AckRequest;
import com.corundumstudio.socketio.SocketIOClient;
import com.corundumstudio.socketio.SocketIOServer;
import com.corundumstudio.socketio.listener.ConnectListener;
import com.corundumstudio.socketio.listener.DataListener;
import com.corundumstudio.socketio.listener.DisconnectListener;
import com.synrgy.binarfud.model.Message;

@Component
public class NotificationController {
    private final SocketIOServer socketIOServer;

    public NotificationController(SocketIOServer socketIOServer) {
        this.socketIOServer = socketIOServer;

        this.socketIOServer.addConnectListener(onUserConnected);
        this.socketIOServer.addDisconnectListener(onUserDisconnected);
        this.socketIOServer.addEventListener("event-order", Message.class, onMessageSent);
    }

    private ConnectListener onUserConnected = socketIOClient -> System.out.println("NotificationController onConnected");
    private DisconnectListener onUserDisconnected = socketIOClient -> System.out.println("NotificationController disconnected"); 

    private DataListener<Message> onMessageSent = new DataListener<Message>() {
        @Override
        public void onData(SocketIOClient client, Message message, AckRequest ackSender) throws Exception {
            socketIOServer.getBroadcastOperations()
                .sendEvent(message.getTo(), message.getContent());
            ackSender.sendAckData("Message sent "+message.getTo());
        }
        
    };
}
