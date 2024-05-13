package com.synrgy.binarfud.Binarfud.payload;

import lombok.Data;

import java.util.UUID;

@Data
public class UserDto {
    private UUID id;

    private String name;

    private String username;

    private String emailAddress;

    private String password;
}
