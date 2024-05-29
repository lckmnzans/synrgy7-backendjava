package org.example.service;

import org.junit.jupiter.api.Assertions;
import org.junit.jupiter.api.DisplayName;
import org.junit.jupiter.api.Test;

import java.util.Map;

class OrdersServiceImplTest {

    @Test
    @DisplayName("Positive case - menambahkan menu serta jumlahnya dan melihat menu yang dipesan")
    void setAndGetMapOrders() {
        OrdersServiceImpl ordersImpl = new OrdersServiceImpl();

        String menuName = "Nasi Goreng";
        int[] menuQtyAndPrice = {2, 20000};

        ordersImpl.setMapOrderedMenu(menuName, menuQtyAndPrice);
        Map<String, int[]> orders = ordersImpl.getMapOrderedMenu();
        Assertions.assertNotNull(orders);
        Assertions.assertTrue(orders.containsKey(menuName));
        Assertions.assertArrayEquals(menuQtyAndPrice, orders.get(menuName));
    }

    @Test
    @DisplayName("Positive case - mengosongkan list order")
    void clearOrders() {
        OrdersServiceImpl ordersImpl = new OrdersServiceImpl();
        ordersImpl.setMapOrderedMenu("Nasi Goreng", new int[]{2, 20000});
        ordersImpl.clearOrderedMenu();

        Assertions.assertTrue(ordersImpl.isOrderedMenuEmpty());
    }
}