package com.synrgy.binarfud.Binarfud.service;

import org.springframework.stereotype.Service;

import java.util.UUID;

@Service
public class InvoiceServiceImpl implements InvoiceService {

    final
    OrderService orderService;

    final
    UserService userService;

    public InvoiceServiceImpl(OrderService orderService, UserService userService) {
        this.orderService = orderService;
        this.userService = userService;
    }

    @Override
    public void generateInvoice(UUID userId, UUID orderId) {

    }
}
