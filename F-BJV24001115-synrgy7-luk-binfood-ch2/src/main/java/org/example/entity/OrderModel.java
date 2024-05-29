package org.example.entity;

public interface OrderModel {
    boolean addOrders(String menu, int quantity);
    int[][] getOrders();
    void clearOrders();
}
