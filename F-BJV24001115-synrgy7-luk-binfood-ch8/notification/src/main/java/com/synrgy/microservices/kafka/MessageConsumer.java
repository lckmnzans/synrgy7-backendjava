package com.synrgy.microservices.kafka;

import com.corundumstudio.socketio.SocketIOServer;
import com.fasterxml.jackson.databind.ObjectMapper;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.kafka.annotation.KafkaListener;
import org.springframework.stereotype.Component;

import java.util.HashMap;

@Component
public class MessageConsumer {
    @Autowired
    SocketIOServer socketIOServer;

    Logger logger = LoggerFactory.getLogger(MessageConsumer.class);
    
    @KafkaListener(topics = "order-status", groupId = "curr-group-id")
    public void listen(String message) throws Exception {
//        Map<String, String> messageMap = messageDissembler(message);
//        String target = messageMap.get("target");
//        String content = messageMap.get("message");
//        NotifData notifRequest = new NotifData(target, content, "");

        System.out.println("Message received");
        ObjectMapper objectMapper = new ObjectMapper();
        HashMap<String, String> messageMap = objectMapper.readValue(message, HashMap.class);
        System.out.println(messageMap);
    }

//    public Map<String, String> messageDissembler(String message) {
//        Map<String, String> messageBody = new LinkedHashMap<>();
//        String[] pairs = message.split(",");
//        for (String pair: pairs) {
//            String[] keyVal = pair.split(":");
//            if (keyVal.length == 2) {
//                String key = keyVal[0];
//                String val = keyVal[1];
//                messageBody.put(key, val);
//            }
//        }
//        return messageBody;
//    }
}
