package com.synrgy.microservices.controller;

import com.synrgy.microservices.model.NotificationRequest;
import com.synrgy.microservices.model.NotificationResponse;
import com.synrgy.microservices.model.SubscribedClient;
import com.synrgy.microservices.model.SubscribedClientDto;
import com.synrgy.microservices.service.FCMService;
import com.synrgy.microservices.service.SubClientService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RestController;

import java.util.concurrent.ExecutionException;

@RestController
public class NotificationController {
    @Autowired
    FCMService fcmService;

    @Autowired
    SubClientService subClientService;

    @PostMapping("/notification")
    public ResponseEntity<NotificationResponse> sendNotification(@RequestBody NotificationRequest request) throws ExecutionException, InterruptedException {
        fcmService.sendMessageToToken(request);
        return new ResponseEntity<>(new NotificationResponse(HttpStatus.OK.value(), "Notification has been sent"), HttpStatus.OK);
    }

    @PostMapping("/subscribe")
    public ResponseEntity<NotificationResponse> saveToken(@RequestBody SubscribedClientDto subscribedClientDto) {
        SubscribedClient subscribedClient = SubscribedClient.builder()
                .token(subscribedClientDto.getToken())
                .subscribed(true)
                .build();
        subClientService.saveClientToken(subscribedClient);
        return new ResponseEntity<>(new NotificationResponse(HttpStatus.OK.value(), "Client subscribed to notification"), HttpStatus.OK);
    }
}
