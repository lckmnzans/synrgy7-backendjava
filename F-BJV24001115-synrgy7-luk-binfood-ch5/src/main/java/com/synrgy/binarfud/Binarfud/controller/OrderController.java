package com.synrgy.binarfud.Binarfud.controller;

import com.synrgy.binarfud.Binarfud.model.Order;
import com.synrgy.binarfud.Binarfud.model.OrderDetail;
import com.synrgy.binarfud.Binarfud.model.Product;
import com.synrgy.binarfud.Binarfud.model.Users;
import com.synrgy.binarfud.Binarfud.service.OrderDetailService;
import com.synrgy.binarfud.Binarfud.service.OrderService;
import com.synrgy.binarfud.Binarfud.service.ProductService;
import com.synrgy.binarfud.Binarfud.service.UserService;
import lombok.extern.slf4j.Slf4j;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Component;

import java.time.Instant;
import java.util.ArrayList;
import java.util.Date;
import java.util.List;

@Component
@Slf4j
public class OrderController {
    @Autowired
    private OrderService orderService;

    @Autowired
    private OrderDetailService orderDetailService;

    @Autowired
    private UserService userService;

    @Autowired
    private ProductService productService;

    private List<OrderDetail> orderDetailList = new ArrayList<>();

    public void test() {
        String username = "lckmnzans";
        String destinationAddress = "Jl Tj Sari VI Sumurboto, Kec Banyumanik, Kota Semarang, Jawa Tengah";
        List<OrderDetail> orders = new ArrayList<>();
        orders.add(createOrderDetail("product id1", 2));
        orders.add(createOrderDetail("product id2", 1));
        createOrder(username, destinationAddress, orders);
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

    public void clearOrderDetail() {
        orderDetailList = new ArrayList<>();
    }

    public void fillOrderDetail(Product product, int qty) {
        orderDetailList.add(createOrderDetail(product, qty));
    }

    public List<OrderDetail> getListOfOrderDetail() {
        return orderDetailList;
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

    public void showAllOrdersDetail() {
        List<OrderDetail> orderDetailList = orderDetailService.getAllOrdersDetail();
        orderDetailList.forEach(orderDetail -> log.info(orderDetail.getId().toString()));
    }

    public void showAllOrdersDetailPageable(int pageNumber, int pageAmount) {
        List<OrderDetail> orderDetailList = orderDetailService.getAllOrdersDetailPageable(pageNumber, pageAmount);
        orderDetailList.forEach(orderDetail -> System.out.println(orderDetail.getProduct().getProductName() +" | "+ orderDetail.getQuantity() +" | "+ orderDetail.getTotalPrice()));
    }

    public void showOrderDetail(String orderId) {
        Order order = orderService.getOrder(orderId);
        order.getOrderDetailList().forEach(orderDetail -> System.out.println(orderDetail.getProduct().getProductName() +" | "+ orderDetail.getQuantity() +" | "+ orderDetail.getTotalPrice()));
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
