package org.example.service;


import java.util.Map;

public interface OrdersService {
    void setMapOrderedMenu(String menuName, int[] menuQtyAndPrice);
    Map<String, int[]> getMapOrderedMenu();
    void clearOrderedMenu();
    boolean isOrderedMenuEmpty();

}
