package com.synrgy.microservices.model;

import jakarta.persistence.*;
import lombok.*;
import lombok.experimental.Accessors;

import java.util.UUID;

@Getter
@Setter
@Builder
@AllArgsConstructor
@NoArgsConstructor
@Accessors(chain = true)
public class SubscribedClientDto {
    private String token;
    private Boolean subscribed = Boolean.TRUE;
}
