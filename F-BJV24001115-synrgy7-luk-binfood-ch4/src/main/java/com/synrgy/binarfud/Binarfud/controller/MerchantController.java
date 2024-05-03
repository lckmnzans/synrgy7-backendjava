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

    public void test() {
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

    public void showMerchantDetail(String merchantId) {
        Merchant merchant;
        try {
            merchant = merchantService.getMerchantById(merchantId);
            merchant.getProductList().forEach(product -> System.out.println(product.getProductName()));
        } catch (RuntimeException e) {
            log.error(e.getLocalizedMessage());
        }
    }

    public void showAllMerchants(@Nullable Boolean isOpen) {
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

    public void showAllMerchants() {
        showAllMerchants(null);
    }

    public void editMerchantsOpenStatus(String merchantId, boolean isOpened) {
        Merchant merchant;
        try {
            merchant = merchantService.getMerchantById(merchantId);
            merchant.setOpen(isOpened);
            merchantService.updateMerchant(merchant);
        } catch (RuntimeException e) {
            log.error(e.getLocalizedMessage());
        }
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
        }

    }
}
