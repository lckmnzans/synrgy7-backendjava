package com.synrgy.binarfud.Binarfud.payload;

import lombok.Data;

@Data
public class LoginRequestDto {
    private String username;
    private String password;
}
