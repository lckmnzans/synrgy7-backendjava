package com.synrgy.microservices.config;

import org.springframework.beans.factory.annotation.Value;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;

import com.corundumstudio.socketio.SocketIOServer;

import jakarta.annotation.PreDestroy;

@Configuration
public class SocketIOConfiguration {
    private SocketIOServer socketIOServer;

    @Value("${socket-io.hostname}")
    private String hostname;

    @Value("${socket-io.port}")
    private int port;

    @Bean
    public SocketIOServer socketIOServer() {
        com.corundumstudio.socketio.Configuration config = new com.corundumstudio.socketio.Configuration();
        config.setHostname(hostname);
        config.setPort(port);
        socketIOServer = new SocketIOServer(config);
        socketIOServer.start();

        socketIOServer.addConnectListener(socketIOClient -> 
            System.out.println("A new client is connected to "+ socketIOClient.getSessionId())
        );

        return socketIOServer;
    }

    @PreDestroy
    public void stopSocketIOServer() {
        socketIOServer.stop();
    }
}
