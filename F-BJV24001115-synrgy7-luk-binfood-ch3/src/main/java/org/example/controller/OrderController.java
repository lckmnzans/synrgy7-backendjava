package org.example.controller;

import org.example.service.OrdersService;
import org.example.view.OrderView;

abstract class OrderController {
    protected OrdersService model;
    protected OrderView view;

    public OrderController(OrdersService model, OrderView view) {
        this.model = model;
        this.view = view;
    }
}
