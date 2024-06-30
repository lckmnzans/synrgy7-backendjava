package com.synrgy.binarfud.Binarfud.kafka;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.kafka.core.KafkaTemplate;
import org.springframework.stereotype.Component;

@Component
public class MessageProducer {
    
    @Autowired
    private KafkaTemplate<String, String> kafkaTemplate;

    public void sendMessage(String topic, String message) {
        kafkaTemplate.send(topic, message);
    }

    public String messageAssembler(String target, String message) {
        StringBuilder messageBody = new StringBuilder();
        messageBody.append("target:"+target);
        messageBody.append(",");
        messageBody.append("message:"+message);
        return messageBody.toString();
    }
}