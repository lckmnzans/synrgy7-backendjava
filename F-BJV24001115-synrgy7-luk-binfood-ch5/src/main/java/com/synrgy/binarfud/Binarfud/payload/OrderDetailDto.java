package com.synrgy.binarfud.Binarfud.payload;

import com.synrgy.binarfud.Binarfud.model.Order;
import lombok.Data;

import java.util.UUID;

@Data
public class OrderDetailDto {
    private String id;
    private int quantity;
}
