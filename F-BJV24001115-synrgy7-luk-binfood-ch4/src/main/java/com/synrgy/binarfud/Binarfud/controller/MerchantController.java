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
    @Autowired
    private MerchantService merchantService;

    public void init() {
        createMerchant("Geprek Jago", "Jl Tj Sari VI No.29, Sumurboto, Banyumanik, Kota Semarang");
        createMerchant("Bakmi 99 Pak Joko", "Jl Sumur Boto Bar. III, Sumurboto, Banyumanik, Kota Semarang");
    }

    public void createMerchant(String merchantName, String merchantLocation) {
        Merchant merchant = Merchant.builder()
                .merchantName(merchantName)
                .merchantLocation(merchantLocation)
                .build();
        merchantService.insertMerchant(merchant);
    }

    public void showAllMerchant(@Nullable Boolean isOpen) {
        List<Merchant> merchantList;

        if (isOpen == null) {
            merchantList = merchantService.getAllMerchant();
        } else {
            merchantList = merchantService.getAllMerchantFilter(isOpen);
        }

        if (merchantList.isEmpty()) {
            System.out.println("Merchant tidak ditemukan");
        } else {
            merchantList.forEach(merchant -> System.out.println("Merchant name :" + merchant.getMerchantName()));
        }
    }

    public void showAllMerchant() {
        showAllMerchant(null);
    }

    public void editMerchantOpenStatus(String merchantId, boolean isOpened) {
        Merchant merchant;
        try {
            merchant = merchantService.getMerchantById(merchantId);
            merchant.setOpen(isOpened);
            merchantService.updateMerchant(merchant);
        } catch (RuntimeException e) {
            log.warn(e.getLocalizedMessage());
        }
    }

    public void deleteMerchant(String merchantId) {
        Merchant merchant;
        try {
            merchant = merchantService.getMerchantById(merchantId);
            merchantService.hardDeleteMerchant(merchant);
            System.out.println("Operasi delete Merchant sukses");
        } catch (RuntimeException e) {
            log.warn(e.getLocalizedMessage());
            System.out.println("Operasi delete Merchant gagal");
        }

    }
}