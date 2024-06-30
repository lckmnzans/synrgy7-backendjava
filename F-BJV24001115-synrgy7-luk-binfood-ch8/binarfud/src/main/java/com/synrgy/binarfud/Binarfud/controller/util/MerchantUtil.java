package com.synrgy.binarfud.Binarfud.controller.util;

import com.synrgy.binarfud.Binarfud.model.Merchant;
import com.synrgy.binarfud.Binarfud.model.Order;
import com.synrgy.binarfud.Binarfud.model.OrderDetail;
import com.synrgy.binarfud.Binarfud.service.MerchantService;
import com.synrgy.binarfud.Binarfud.service.OrderService;
import jakarta.annotation.Nullable;
import lombok.extern.slf4j.Slf4j;
import org.springframework.stereotype.Component;

import java.util.Date;
import java.util.List;

@Component
@Slf4j
public class MerchantUtil {
    private final MerchantService merchantService;
    private final OrderService orderService;

    public MerchantUtil(MerchantService merchantService, OrderService orderService) {
        this.merchantService = merchantService;
        this.orderService = orderService;
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

    public Double getMerchantReport(String merchantId, Date startDate, Date endDate) {
        Merchant merchant = merchantService.getMerchantById(merchantId);
        List<Order> orderListInBetween = orderService.getAllOrdersInBetween(startDate, endDate);
        List<OrderDetail> orderDetailList = orderListInBetween.stream()
                .flatMap(order -> order.getOrderDetailList().stream())
                .toList();
        List<OrderDetail> filteredOrderDetailList = orderDetailList.stream()
                .filter(orderDetail -> orderDetail.getProduct().getMerchant() == merchant)
                .toList();
        filteredOrderDetailList.forEach(orderDetail -> log.info(orderDetail.getProduct().getProductName()));
        Double total = filteredOrderDetailList.stream()
                .map(orderDetail -> orderDetail.getTotalPrice()).reduce(0.0, Double::sum);

        return total;
    }
}
