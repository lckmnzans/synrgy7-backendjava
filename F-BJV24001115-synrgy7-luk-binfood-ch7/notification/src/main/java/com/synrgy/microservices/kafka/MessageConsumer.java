package com.synrgy.microservices.kafka;

import java.util.LinkedHashMap;
import java.util.Map;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.kafka.annotation.KafkaListener;
import org.springframework.stereotype.Component;

import com.corundumstudio.socketio.SocketIOClient;
import com.corundumstudio.socketio.SocketIOServer;
import com.synrgy.microservices.model.NotifData;

@Component
public class MessageConsumer {
    @Autowired
    SocketIOServer socketIOServer;

    @Autowired
    SocketIOClient socketIOClient;
    
    @KafkaListener(topics = "order-status", groupId = "curr-group-id")
    public void listen(String message) {
        Map<String, String> messageMap = messageDeassembler(message);
        String target = messageMap.get("target");
        String content = messageMap.get("message");
        NotifData notifData = new NotifData(target, content);
        System.out.println("Message received for : " + notifData.getTarget());
    }

    public Map<String, String> messageDeassembler(String message) {
        Map<String, String> messageBody = new LinkedHashMap<>();
        String[] pairs = message.split(",");
        for (String pair: pairs) {
            String[] keyVal = pair.split(":");
            if (keyVal.length == 2) {
                String key = keyVal[0];
                String val = keyVal[1];
                messageBody.put(key, val);
            }
        }
        return messageBody;
    }
}
