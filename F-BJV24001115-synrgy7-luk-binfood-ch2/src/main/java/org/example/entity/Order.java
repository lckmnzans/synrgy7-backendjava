package org.example.entity;

import java.util.Arrays;

import static org.example.Const.menuPrice;

public class Order implements OrderModel {
    private final int[][] orderedMenu = new int[2][8];

    @Override
    public boolean addOrders(String menu, int quantity) {
        int menuId = Integer.parseInt(menu);
        orderedMenu[0][menuId-1] = quantity;
        orderedMenu[1][menuId-1] = menuPrice[menuId-1] * quantity;
        return true;
    }

    @Override
    public int[][] getOrders() {
        return orderedMenu;
    }

    @Override
    public void clearOrders() {
        Arrays.fill(orderedMenu, 0);
    }
}
