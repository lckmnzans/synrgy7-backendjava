package com.synrgy.binarfud.Binarfud.controller;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import com.synrgy.binarfud.Binarfud.kafka.MessageProducer;

@RestController
@RequestMapping("message")
public class MessageController {
    @Autowired
    MessageProducer messageProducer;

    @GetMapping("/send/{msg}")
    public String sendMessage(@PathVariable("msg") String msg) {
        messageProducer.sendMessage("order", msg);
        return "Message sent to topic " + "order";
    }
}
