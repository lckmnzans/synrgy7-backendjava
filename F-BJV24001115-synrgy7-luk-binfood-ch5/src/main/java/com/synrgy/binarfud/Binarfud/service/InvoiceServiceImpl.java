package com.synrgy.binarfud.Binarfud.service;

import com.synrgy.binarfud.Binarfud.model.Order;
import com.synrgy.binarfud.Binarfud.model.Users;
import lombok.extern.slf4j.Slf4j;
import org.springframework.stereotype.Service;

import java.util.UUID;

@Service
@Slf4j
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
    public void generateInvoice(String userId, String orderId) {
        Users user;
        Order order;
        try {
            user = userService.getUserById(userId);
            order = orderService.getOrder(orderId);
        } catch (RuntimeException e) {
            log.error(e.getLocalizedMessage());
        }
    }
}
