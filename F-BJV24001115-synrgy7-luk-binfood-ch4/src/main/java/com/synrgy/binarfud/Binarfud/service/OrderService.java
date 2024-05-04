package com.synrgy.binarfud.Binarfud.service;

import com.synrgy.binarfud.Binarfud.model.Order;
import com.synrgy.binarfud.Binarfud.model.Users;

import java.util.List;

public interface OrderService {
    void insertOrder(Order order);

    Order getOrder(String orderId);

    List<Order> getAllOrders();

    List<Order> getAllOrdersByUser(Users user);

    Order updateOrderStatus(Order order);

    void deleteOrder(Order order);
}
