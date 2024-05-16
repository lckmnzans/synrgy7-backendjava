package com.synrgy.binarfud.Binarfud.controller;

import com.synrgy.binarfud.Binarfud.model.Merchant;
import com.synrgy.binarfud.Binarfud.service.MerchantService;
import jakarta.annotation.Nullable;
import lombok.extern.slf4j.Slf4j;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Component;

import java.util.List;

@Component
@Slf4j
public class MerchantController {
    private final MerchantService merchantService;

    public MerchantController(MerchantService merchantService) {
        this.merchantService = merchantService;
    }

    public Merchant createMerchant(String merchantName, String merchantLocation) {
        Merchant merchant = Merchant.builder()
                .merchantName(merchantName)
                .merchantLocation(merchantLocation)
                .build();
        return merchantService.insertMerchant(merchant);
    }

    public Merchant getMerchantDetail(String merchantId) {
        Merchant merchant;
        try {
            merchant = merchantService.getMerchantById(merchantId);
            merchant.getProductList().forEach(product -> System.out.println(product.getProductName()));
            return merchant;
        } catch (RuntimeException e) {
            log.error(e.getLocalizedMessage());
            throw e;
        }
    }

    public List<Merchant> getAllMerchants(@Nullable Boolean isOpen) {
        List<Merchant> merchantList;

        if (isOpen == null) {
            merchantList = merchantService.getAllMerchant();
        } else {
            merchantList = merchantService.getAllMerchantFilter(isOpen);
        }

        if (merchantList.isEmpty()) {
            System.out.println("Merchant tidak ditemukan");
        } else {
            merchantList.forEach(merchant -> log.info("Merchant name :" + merchant.getMerchantName()));
        }
        return merchantList;
    }

    public List<Merchant> getAllMerchants() {
        return getAllMerchants(null);
    }

    public Merchant editMerchantOpenStatus(String id, boolean isOpened) {
        Merchant merchant;
        try {
            merchant = merchantService.getMerchantById(id);
            merchant.setOpen(isOpened);
            return merchantService.updateMerchant(id, merchant);
        } catch (RuntimeException e) {
            log.error(e.getLocalizedMessage());
        }
        return null;
    }

    public void deleteMerchant(String merchantId) {
        Merchant merchant;
        try {
            merchant = merchantService.getMerchantById(merchantId);
            merchantService.hardDeleteMerchant(merchant);
            System.out.println("Operasi delete Merchant sukses");
        } catch (RuntimeException e) {
            log.error(e.getLocalizedMessage());
            System.out.println("Operasi delete Merchant gagal");
            throw e;
        }
    }
}
