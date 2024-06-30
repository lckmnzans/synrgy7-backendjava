package com.synrgy.microservices.model;

import lombok.Data;
import lombok.Getter;
import lombok.Setter;
import org.springframework.boot.autoconfigure.domain.EntityScan;

@Getter
@Setter
@Data
public class UserToken {
    private String token;
    private boolean subs;
}
