package org.example.controller;

import org.example.entity.OrderModel;
import org.example.view.OrderView;

abstract class OrderController {
    protected OrderModel model;
    protected OrderView view;

    public OrderController(OrderModel model, OrderView view) {
        this.model = model;
        this.view = view;
    }
}
