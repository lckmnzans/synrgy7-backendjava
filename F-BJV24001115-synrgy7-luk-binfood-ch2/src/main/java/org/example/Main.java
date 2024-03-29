package org.example;

import org.example.controller.ConsoleOrderController;
import org.example.entity.Order;
import org.example.entity.OrderModel;
import org.example.view.ConsoleOrderView;
import org.example.view.OrderView;

public class Main {
    public static void main(String[] args) {
        OrderModel orderModel = new Order();
        OrderView orderView = new ConsoleOrderView();
        ConsoleOrderController orderController = new ConsoleOrderController(orderModel, orderView);
    }
}