package com.synrgy.binarfud.Binarfud.service;

import com.synrgy.binarfud.Binarfud.model.Order;
import com.synrgy.binarfud.Binarfud.model.OrderDetail;
import com.synrgy.binarfud.Binarfud.model.Users;
import lombok.extern.slf4j.Slf4j;
import org.springframework.stereotype.Service;

import java.util.List;
import java.util.UUID;

@Service
@Slf4j
public class InvoiceServiceImpl implements InvoiceService {

    final
    OrderService orderService;

    final
    UserService userService;

    final
    JasperService jasperService;

    public InvoiceServiceImpl(OrderService orderService, UserService userService, JasperService jasperService) {
        this.orderService = orderService;
        this.userService = userService;
        this.jasperService = jasperService;
    }

    @Override
    public byte[] generateInvoice(String userId, String orderId) {
        Users user;
        Order order;
        byte[] invoice = null;
        try {
            user = userService.getUserById(userId);
            order = orderService.getOrder(orderId);
            List<OrderDetail> orderDetailList = order.getOrderDetailList();
            invoice = jasperService.generate(orderDetailList, user, order, "pdf");
        } catch (RuntimeException e) {
            log.error(e.getLocalizedMessage());
        }
        return invoice;
    }
}
