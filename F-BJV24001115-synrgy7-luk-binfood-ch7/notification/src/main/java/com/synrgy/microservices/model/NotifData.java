package com.synrgy.microservices.model;

import lombok.Data;

@Data
public class NotifData {
    private String target;
    private String message;
    
    public NotifData(String target, String message) {
        this.target = target;
        this.message = message;
    }
}
