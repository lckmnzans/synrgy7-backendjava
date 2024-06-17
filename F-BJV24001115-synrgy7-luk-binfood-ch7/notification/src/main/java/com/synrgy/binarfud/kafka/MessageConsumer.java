package com.synrgy.binarfud.kafka;

import org.springframework.kafka.annotation.KafkaListener;
import org.springframework.stereotype.Component;

@Component
public class MessageConsumer {
    
    @KafkaListener(topics = "order-status", groupId = "curr-group-id")
    public void listen(String message) {
        System.out.println("Message received : " + message);
    }
}
