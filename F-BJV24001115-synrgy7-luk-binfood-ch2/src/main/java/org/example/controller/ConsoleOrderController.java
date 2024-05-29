package org.example.controller;

import org.example.entity.OrderModel;
import org.example.helper.FileHelper;
import org.example.view.OrderView;

import java.util.Arrays;
import java.util.List;
import java.util.Objects;

public class ConsoleOrderController extends OrderController {
    private static final FileHelper fileHelper = new FileHelper();

    public ConsoleOrderController(OrderModel model, OrderView view) {
        super(model, view);
        init();
    }

    void init() {
        mainMenuProcess();
    }

    public void mainMenuProcess() {
        String userInput = view.displayMainMenu();
        List<String> listOfMenuItem = Arrays.asList("1","2","3","4","5","6","7","8");
        List<String> subMenuOption = Arrays.asList("00","01");
        if (listOfMenuItem.contains(userInput)) {
            String menuId = userInput;
            String menuName = view.displaySelectedMenu(menuId);
            int menuQty = view.displaySelectedMenuQty(menuId, menuName);
            if (menuQty != 0) {
                model.addOrders(menuId, menuQty);
                System.out.println("=".repeat(30));
                System.out.println("Menu berhasil ditambahkan ke daftar pesanan anda");
                System.out.println("=".repeat(30));
                mainMenuProcess();
            } else {
                System.out.println("=".repeat(30));
                System.out.println("Pesanan menu dibatalkan, kembali ke menu utama");
                System.out.println("=".repeat(30));
                mainMenuProcess();
            }
        } else if (subMenuOption.contains(userInput)) {
            subMenuProcess(userInput);
        } else {
            System.out.println("Maaf, masukkan menu atau pilihan yang sesuai! \n" + "=".repeat(30));
            mainMenuProcess();
        }
    }

    public void subMenuProcess(String menu) {
        if (Objects.equals(menu, "00")) {
            boolean isOrderEmpty = isOrderEmpty();
            if (!isOrderEmpty) {
                int[][] item = model.getOrders();
                String invoice = view.displayOrderedMenu(item);
                if (invoice != null) {
                    System.out.println("Pesanan anda terkonfirmasi");
                    fileHelper.writeFile(invoice);
                } else {
                    System.out.println("Pemesanan anda dibatalkan dan anda akan kembali ke menu utama");
                    mainMenuProcess();
                }
            } else {
                System.out.println("=".repeat(30));
                System.out.println("Anda belum memesan menu apapun");
                System.out.println("=".repeat(30));
                mainMenuProcess();
            }
        } else {
            System.out.println("=".repeat(30));
            System.out.println("Pemesanan anda dibatalkan dan anda akan keluar aplikasi");
            System.out.println("=".repeat(30));
            System.out.println();
            System.exit(0);
        }
    }

    public boolean isOrderEmpty() {
        int[][] orderedMenu = model.getOrders();
        int countOrderedItem = 0;
        for (int i=0; i < orderedMenu[0].length; i++) {
            if (orderedMenu[0][i] != 0) {
                countOrderedItem++;
            }
        }
        return countOrderedItem == 0;
    }
}
