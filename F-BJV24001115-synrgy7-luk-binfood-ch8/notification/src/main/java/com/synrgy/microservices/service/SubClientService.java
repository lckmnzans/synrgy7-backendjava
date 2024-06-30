package com.synrgy.microservices.service;

import com.synrgy.microservices.model.SubscribedClient;
import com.synrgy.microservices.repository.SubscribedClientRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.List;

@Service
public class SubClientService {
    @Autowired
    SubscribedClientRepository subscribedClientRepository;

    public void saveClientToken(SubscribedClient subscribedClient) {
        subscribedClientRepository.save(subscribedClient);
    }

    public List<String> getAllClientToken() {
        List<SubscribedClient> subscribedClients = subscribedClientRepository.findAll();
        List<String> tokens = subscribedClients.stream().map(SubscribedClient::getToken).toList();
        return tokens;
    }
}
