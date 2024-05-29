package org.example;

import java.util.Arrays;
import java.util.Objects;

import static org.example.Const.menuPrice;

public class Order {
    private static Order order;
    private final int[][] orderedMenu = new int[2][8];
    public final static String PRICE = "price";
    public final static String QTY = "qty";

    public static Order getInstance() {
        if (order == null) {
            order = new Order();
        }
        return order;
    }

    public void addOrder(String menu, int qty) {
        int menuId = Integer.parseInt(menu);
        orderedMenu[0][menuId-1] = qty;
        orderedMenu[1][menuId-1] = menuPrice[menuId-1] * qty;
        System.out.println("Menu berhasil ditambahkan ke daftar pesanan anda");
    }

    public int[][] getOrders() {
        return orderedMenu;
    }

    public int getOrder(String menu, String lookup) {
        int menuId = Integer.parseInt(menu);
        if (Objects.equals(lookup, PRICE)) {
            return orderedMenu[1][menuId-1];
        } else {
            return orderedMenu[0][menuId-1];
        }
    }

    public void clearOrder() {
        Arrays.fill(orderedMenu, 0);
    }


}
