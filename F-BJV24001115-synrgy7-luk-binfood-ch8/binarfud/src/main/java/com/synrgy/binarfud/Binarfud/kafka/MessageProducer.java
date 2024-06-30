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

    public String setData(String key, String value) {
        return setKey(key)+setValue(value);
    }

    private String setKey(String key) {
        return "\""+key+"\":";
    }

    private String setValue(String value) {
        return "\""+value+"\"";
    }
}
