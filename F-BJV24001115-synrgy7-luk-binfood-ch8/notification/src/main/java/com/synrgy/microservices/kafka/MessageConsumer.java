package com.synrgy.microservices.kafka;

import com.corundumstudio.socketio.SocketIOServer;
import com.fasterxml.jackson.databind.ObjectMapper;
import com.synrgy.microservices.model.NotificationRequest;
import com.synrgy.microservices.service.FCMService;
import com.synrgy.microservices.service.SubClientService;
import org.aspectj.weaver.ast.Not;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.kafka.annotation.KafkaListener;
import org.springframework.stereotype.Component;

import java.util.HashMap;
import java.util.List;
import java.util.concurrent.ExecutionException;

@Component
public class MessageConsumer {
    final
    SocketIOServer socketIOServer;

    final
    FCMService fcmService;

    final
    SubClientService subClientService;

    private final List<String> clientsToken;

    Logger logger = LoggerFactory.getLogger(MessageConsumer.class);

    public MessageConsumer(SocketIOServer socketIOServer, FCMService fcmService, SubClientService subClientService) {
        this.socketIOServer = socketIOServer;
        this.fcmService = fcmService;
        this.subClientService = subClientService;
        this.clientsToken = subClientService.getAllClientToken();
    }

    @KafkaListener(topics = "order-status", groupId = "curr-group-id")
    public void listen(String message) throws Exception {
        ObjectMapper objectMapper = new ObjectMapper();
        HashMap<String, String> messageMap = objectMapper.readValue(message, HashMap.class);
        System.out.println(messageMap);
    }

    @KafkaListener(topics = "promo-notif", groupId = "curr-group-id")
    public void listenNotifMessage(String message) throws Exception {
        ObjectMapper objectMapper = new ObjectMapper();
        HashMap<String, String> messageMap = objectMapper.readValue(message, HashMap.class);
        String title = messageMap.get("title");
        String body = messageMap.get("body");
        String topic = messageMap.get("topic");

        blastNotification(title, body, topic);
        System.out.println("Promo has been sent");
    }

    private void blastNotification(String title, String body, String topic) {
        NotificationRequest notificationRequest = new NotificationRequest();
        notificationRequest.setTitle(title);
        notificationRequest.setBody(body);
        notificationRequest.setTopic(topic);
        clientsToken.forEach(token -> {
            notificationRequest.setToken(token);
            try {
                fcmService.sendMessageToToken(notificationRequest);
            } catch (InterruptedException | ExecutionException e) {
                throw new RuntimeException(e);
            }
        });
    }
}
