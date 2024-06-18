package com.synrgy.binarfud.model;

import lombok.Data;

@Data
public class Message {
    private String from;
    private String to;
    private String content;
}