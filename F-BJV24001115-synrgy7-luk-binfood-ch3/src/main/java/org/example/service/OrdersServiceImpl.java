package org.example.service;

import java.util.*;

public class OrdersServiceImpl implements OrdersService {
    private final Map<String, int[]> mapOrderedMenu = new HashMap<>();

    public void setMapOrderedMenu(String menuName, int[] menuQtyAndPrice) {
        mapOrderedMenu.put(menuName, menuQtyAndPrice);
    }

    public Map<String, int[]> getMapOrderedMenu() {
        return mapOrderedMenu;
    }

    @Override
    public void clearOrderedMenu() {
        mapOrderedMenu.clear();
    }

    @Override
    public boolean isOrderedMenuEmpty() {
        return mapOrderedMenu.keySet().isEmpty();
    }
}
