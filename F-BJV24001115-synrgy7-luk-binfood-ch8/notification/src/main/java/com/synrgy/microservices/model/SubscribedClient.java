package com.synrgy.microservices.model;

import jakarta.persistence.*;
import lombok.*;
import lombok.experimental.Accessors;
import org.apache.kafka.common.protocol.types.Field;

import java.util.UUID;

@Getter
@Setter
@Builder
@AllArgsConstructor
@NoArgsConstructor
@Accessors(chain = true)
@Entity
@Table(name = "subscribed_client")
public class SubscribedClient {
    @Id
    @GeneratedValue(strategy = GenerationType.UUID)
    private UUID id;

    private String token;
    private Boolean subscribed = Boolean.TRUE;
}
