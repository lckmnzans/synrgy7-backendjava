package com.synrgy.binarfud.Binarfud.controller;

import com.synrgy.binarfud.Binarfud.kafka.MessageProducer;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

@RestController
@RequestMapping("message")
public class MessageController {
    @Autowired
    MessageProducer messageProducer;

    @GetMapping("/send/{msg}")
    public String sendMessage(@PathVariable("msg") String msg) {
        StringBuilder message = new StringBuilder();
        message.append("{");
        message.append(messageProducer.setData("target", "client"));
        message.append(",");
        message.append(messageProducer.setData("message", msg));
        message.append("}");
        messageProducer.sendMessage("order-status", message.toString());
        return "Message sent to topic " + "order";
    }
}
