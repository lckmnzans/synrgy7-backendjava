package com.synrgy.microservices.kafka;

import java.util.LinkedHashMap;
import java.util.Map;

import org.springframework.kafka.annotation.KafkaListener;
import org.springframework.stereotype.Component;

@Component
public class MessageConsumer {
    
    @KafkaListener(topics = "order-status", groupId = "curr-group-id")
    public void listenOrder(String message) {
        Map<String, String> messageDeassembled = messageDeassembler(message);
        System.out.println("Message received to : " + messageDeassembled.get("target"));
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
