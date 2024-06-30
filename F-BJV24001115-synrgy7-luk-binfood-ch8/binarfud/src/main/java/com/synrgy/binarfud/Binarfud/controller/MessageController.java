package com.synrgy.binarfud.Binarfud.controller;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.*;

import com.synrgy.binarfud.Binarfud.kafka.MessageProducer;

@RestController
@RequestMapping("message")
public class MessageController {
    @Autowired
    MessageProducer messageProducer;

    @GetMapping("/send/{msg}")
    public String sendMessage(@PathVariable("msg") String msg) {
        String message = messageProducer.messageAssembler("client", msg);
        messageProducer.sendMessage("order-status", message);
        return "Message sent to topic " + "order";
    }
}
