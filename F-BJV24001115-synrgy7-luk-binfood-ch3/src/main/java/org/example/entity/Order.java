package org.example.entity;

import java.time.LocalTime;

public class Order {
    public Order(int id, LocalTime orderTime, String destinationAddress, int userId, boolean completed) {
        this.id = id;
        this.orderTime = orderTime;
        this.destinationAddress = destinationAddress;
        this.userId = userId;
        this.completed = completed;
    }

    public int getId() {
        return id;
    }

    public void setId(int id) {
        this.id = id;
    }

    public LocalTime getOrderTime() {
        return orderTime;
    }

    public void setOrderTime(LocalTime orderTime) {
        this.orderTime = orderTime;
    }

    public String getDestinationAddress() {
        return destinationAddress;
    }

    public void setDestinationAddress(String destinationAddress) {
        this.destinationAddress = destinationAddress;
    }

    public int getUserId() {
        return userId;
    }

    public void setUserId(int userId) {
        this.userId = userId;
    }

    public boolean isCompleted() {
        return completed;
    }

    public void setCompleted(boolean completed) {
        this.completed = completed;
    }

    private int id;
    private LocalTime orderTime;
    private String destinationAddress;
    private int userId;
    private boolean completed;
}
