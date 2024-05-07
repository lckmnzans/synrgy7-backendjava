package com.synrgy.binarfud.Binarfud.service;

import com.synrgy.binarfud.Binarfud.model.Merchant;

import java.util.List;

public interface MerchantService {
    Merchant insertMerchant(Merchant merchant);

    Merchant getMerchantByName(String merchantName);

    Merchant getMerchantById(String id);

    Merchant updateMerchant(Merchant merchant);

    List<Merchant> getAllMerchant();

    List<Merchant> getAllMerchantFilter(boolean isOpen);

    void hardDeleteMerchant(Merchant merchant);
}
