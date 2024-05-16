package com.synrgy.binarfud.Binarfud.controller;

import com.synrgy.binarfud.Binarfud.model.Order;
import com.synrgy.binarfud.Binarfud.model.OrderDetail;
import com.synrgy.binarfud.Binarfud.model.Product;
import com.synrgy.binarfud.Binarfud.model.Users;
import com.synrgy.binarfud.Binarfud.service.*;
import lombok.extern.slf4j.Slf4j;
import org.springframework.stereotype.Component;

import java.time.Instant;
import java.util.Date;
import java.util.List;

@Component
@Slf4j
public class OrderController {
    private final OrderService orderService;

    private final OrderDetailService orderDetailService;

    private final UserService userService;

    private final ProductService productService;

    private final JasperService jasperService;

    public OrderController(OrderService orderService, OrderDetailService orderDetailService, UserService userService, ProductService productService, JasperService jasperService) {
        this.orderService = orderService;
        this.orderDetailService = orderDetailService;
        this.userService = userService;
        this.productService = productService;
        this.jasperService = jasperService;
    }

    public void createOrder(String username, String destinationAddress, List<OrderDetail> orderDetailList) {
        Users user;
        try {
            user = userService.getUserByUsername(username);
        } catch (RuntimeException e) {
            log.error(e.getLocalizedMessage());
            throw e;
        }

        Order order = Order.builder()
                .user(user)
                .orderTime(Date.from(Instant.now()))
                .destinationAddress(destinationAddress)
                .completed(Boolean.FALSE)
                .build();
        orderService.insertOrder(order);

        for (OrderDetail orderDetail: orderDetailList) {
            orderDetail.setOrder(order);
        }

        orderDetailService.createBatchesOrder(orderDetailList);
    }

    public OrderDetail createOrderDetail(String productId, int qty) {
        Product product = productService.getProductById(productId);
        OrderDetail orderDetail = OrderDetail.builder()
                .product(product)
                .quantity(qty)
                .totalPrice(product.getPrice() * qty)
                .build();
        log.info("Order Detail telah dibuat dengan Produk :"+product.getProductName()+" dan Jumlah :"+qty);
        return orderDetail;
    }

    public OrderDetail createOrderDetail(Product product, int qty) {
        OrderDetail orderDetail = OrderDetail.builder()
                .product(product)
                .quantity(qty)
                .totalPrice(product.getPrice() * qty)
                .build();
        log.info("Order Detail telah dibuat dengan Produk :"+product.getProductName()+" dan Jumlah :"+qty);
        return orderDetail;
    }

    public List<Order> getAllOrders() {
        List<Order> orderList = orderService.getAllOrders();
        orderList.forEach(order ->
                log.info(order.getId() +" | "+ order.getOrderTime() +" | "+ order.getUser().getUsername() +" | "+ order.getDestinationAddress())
        );
        return orderList;
    }

    public void getAllOrdersByUser(String username) {
        Users user = userService.getUserByUsername(username);
        List<Order> orderList = orderService.getAllOrdersByUser(user);
        orderList.forEach(order ->
                log.info(order.getId() +" | "+ order.getOrderTime() +" | "+ order.getUser().getUsername() +" | "+ order.getDestinationAddress())
        );
    }

    public void getAllOrdersDetail() {
        List<OrderDetail> orderDetailList = orderDetailService.getAllOrdersDetail();
        orderDetailList.forEach(orderDetail ->
                log.info(orderDetail.getId().toString())
        );
    }

    public void getAllOrdersDetailPageable(int pageNumber, int pageAmount) {
        List<OrderDetail> orderDetailList = orderDetailService.getAllOrdersDetailPageable(pageNumber, pageAmount);
        orderDetailList.forEach(orderDetail ->
                log.info(orderDetail.getProduct().getProductName() +" | "+ orderDetail.getQuantity() +" | "+ orderDetail.getTotalPrice())
        );
    }

    public void getOrderDetail(String orderId) {
        Order order = orderService.getOrder(orderId);
        order.getOrderDetailList().forEach(orderDetail ->
                log.info(orderDetail.getProduct().getProductName() +" | "+ orderDetail.getQuantity() +" | "+ orderDetail.getTotalPrice())
        );
    }

    public void editOrderStatus(String orderId, boolean completedStatus) {
        Order order = orderService.getOrder(orderId);
        order.setCompleted(completedStatus);
        orderService.updateOrderStatus(order);
        log.debug("Order status berhasil dirubah");
    }

    public void deleteOrder(String orderId) {
        Order order = orderService.getOrder(orderId);
        orderService.deleteOrder(order);
    }
}
