package com.synrgy.microservices.service.listener;

import com.corundumstudio.socketio.AckRequest;
import com.corundumstudio.socketio.SocketIOClient;
import com.corundumstudio.socketio.SocketIOServer;
import com.corundumstudio.socketio.listener.DataListener;
import com.synrgy.microservices.model.MessageData;
import org.springframework.stereotype.Service;

@Service
public class MessageDataListener implements DataListener<MessageData> {
    private final SocketIOServer socketIOServer;

    public MessageDataListener(SocketIOServer socketIOServer) {
        this.socketIOServer = socketIOServer;
    }

    @Override
    public void onData(SocketIOClient client, MessageData data, AckRequest ackSender) throws Exception {
        socketIOServer.getBroadcastOperations().sendEvent("product-promo", data.getContent());
        ackSender.sendAckData("Product promo broadcasted");
    }

}
