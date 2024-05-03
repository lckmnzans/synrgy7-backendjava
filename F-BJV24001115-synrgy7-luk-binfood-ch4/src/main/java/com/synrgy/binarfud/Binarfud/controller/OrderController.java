package com.synrgy.binarfud.Binarfud.controller;

import com.synrgy.binarfud.Binarfud.model.Order;
import com.synrgy.binarfud.Binarfud.model.OrderDetail;
import com.synrgy.binarfud.Binarfud.model.Product;
import com.synrgy.binarfud.Binarfud.model.Users;
import com.synrgy.binarfud.Binarfud.service.*;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Component;

import java.time.Instant;
import java.util.ArrayList;
import java.util.Date;
import java.util.List;

@Component
public class OrderController {
    @Autowired
    private OrderService orderService;

    @Autowired
    private OrderDetailService orderDetailService;

    @Autowired
    private UserService userService;

    @Autowired
    private ProductService productService;

    public void test() {
        String username = "lckmnzans";
        List<OrderDetail> orders = new ArrayList<>();
        orders.add(createOrderDetail("ce77b5c7-f060-453c-989a-e1fdb9739871", 2));
        orders.add(createOrderDetail("5314a4ba-e521-4bf6-93ce-59177c2aae1c", 1));
        createOrder(username, "Jl Tj Sari VI No.29, Sumurboto, Banyumanik, Kota Semarang", orders);
    }

    public void createOrder(String username, String destinationAddress, List<OrderDetail> orderDetailList) {
        Users user = userService.getUserByUsername(username);
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

    private OrderDetail createOrderDetail(String productId, int qty) {
        Product product = productService.getProductById(productId);
        OrderDetail orderDetail = OrderDetail.builder()
                .product(product)
                .quantity(qty)
                .totalPrice(product.getPrice() * qty)
                .build();
        return orderDetail;
    }

    public void showAllOrders() {
        List<Order> orderList = orderService.getAllOrders();
        orderList.forEach(order -> System.out.println(order.getId() +" | "+ order.getOrderTime() +" | "+ order.getUser().getUsername() +" | "+ order.getDestinationAddress()));
    }

    public void showAllOrdersByUser(String username) {
        Users user = userService.getUserByUsername(username);
        List<Order> orderList = orderService.getAllOrdersByUser(user);
        orderList.forEach(order -> System.out.println(order.getId() +" | "+ order.getOrderTime() +" | "+ order.getUser().getUsername() +" | "+ order.getDestinationAddress()));
    }

    public void showOrderDetail(String orderId) {
        Order order = orderService.getOrder(orderId);
        order.getOrderDetailList().forEach(orderDetail -> System.out.println(orderDetail.getProduct().getProductName() +" | "+ orderDetail.getQuantity() +" | "+ orderDetail.getTotalPrice()));
    }

    public void editOrderStatus(String orderId, boolean completedStatus) {
        Order order = orderService.getOrder(orderId);
        order.setCompleted(completedStatus);
        orderService.updateOrderStatus(order);
    }
}
