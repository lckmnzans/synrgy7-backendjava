package com.synrgy.binarfud.Binarfud.payload;

import com.synrgy.binarfud.Binarfud.model.Order;
import com.synrgy.binarfud.Binarfud.model.Product;
import lombok.Data;

import java.util.UUID;

@Data
public class OrderDetailDto {
    private String id;
    private String productId;
    private String productName;
    private int quantity;

    public void setProduct(Product product) {
        this.productId = product.getId().toString();
        this.productName = product.getProductName();
    }
}
