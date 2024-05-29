package org.example.view;

public interface OrderView {
    String displayMainMenu();
    String displaySelectedMenu(String menu);
    int displaySelectedMenuQty(String menuId, String menuName);
    String displayOrderedMenu(int[][] item);
}
