package com.synrgy.binarfud.Binarfud.payload;

import lombok.Data;

import java.util.UUID;

@Data
public class MerchantDto {
    private UUID id;

    private String merchantName;
    private String merchantLocation;

    private boolean open;
}
