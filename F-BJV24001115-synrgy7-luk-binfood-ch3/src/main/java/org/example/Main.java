package org.example;

import org.example.controller.ConsoleOrderController;
import org.example.service.OrdersServiceImpl;
import org.example.view.ConsoleOrderView;

public class Main {
    public static void main(String[] args) {
        OrdersServiceImpl ordersImplModel = new OrdersServiceImpl();
        ConsoleOrderView orderView = new ConsoleOrderView();
        ConsoleOrderController orderController = new ConsoleOrderController(ordersImplModel, orderView);
    }
}