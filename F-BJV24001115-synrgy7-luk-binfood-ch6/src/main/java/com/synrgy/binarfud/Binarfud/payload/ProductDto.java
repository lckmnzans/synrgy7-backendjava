package com.synrgy.binarfud.Binarfud.payload;

import com.synrgy.binarfud.Binarfud.model.Merchant;
import lombok.Data;

import java.util.UUID;

@Data
public class ProductDto {
    private UUID id;
    private String productName;
    private Double price;
    private UUID merchant;
    private String merchantName;

    public void setMerchant(Merchant merchant) {
        this.merchant = merchant.getId();
        this.merchantName = merchant.getMerchantName();
    }
}
