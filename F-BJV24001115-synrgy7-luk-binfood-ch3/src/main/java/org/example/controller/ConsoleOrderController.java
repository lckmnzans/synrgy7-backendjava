package org.example.controller;

import org.example.service.OrdersServiceImpl;
import org.example.helper.FileHelper;
import org.example.view.ConsoleOrderView;

import java.util.Map;
import java.util.Objects;
import java.util.Optional;
import java.util.Scanner;

import static org.example.Const.*;

public class ConsoleOrderController extends OrderController {
    private static final FileHelper fileHelper = new FileHelper();
    private final Scanner inputConsole;
    private OrdersServiceImpl model;
    private ConsoleOrderView view;


    public ConsoleOrderController(OrdersServiceImpl model, ConsoleOrderView view) {
        super(model, view);
        this.model = model;
        this.view = view;
        this.inputConsole = new Scanner(System.in);
        init();
    }

    void init() {
        mainMenuProcess();
    }

    void mainMenuProcess() {
        view.displayMainMenu();
        String userInput = getUserInput();

        if (menuOption.contains(userInput)) {
            String menuId = userInput;
            String menuName = getMenuName(Integer.parseInt(menuId));
            view.displaySelectedMenu(menuId, menuName);
            int menuQty = getUserInputInt(() -> {
                view.displayHeader("Maaf, masukkan jumlah pesanan");
                view.displayBody("+ "+menuName);
                view.displayFooter("", "=>");
                inputConsole.nextLine();
            });
            if (view.displayConfirmationContinue(menuQty, menuId, menuName)) {
                if (menuQty != 0) {
                    int[] menuQtyAndPrice = {menuQty, menuQty * menuPrice[Integer.parseInt(menuId) - 1]};
                    model.setMapOrderedMenu(menuName, menuQtyAndPrice);
                    view.displayHeader("Menu berhasil ditambahkan ke daftar pesanan anda");
                    mainMenuProcess();
                }
            } else {
                view.displayHeader("Pesanan menu dibatalkan, kembali ke menu utama");
                mainMenuProcess();
            }
        } else if (subMenuOption.contains(userInput)) {
            subMenuProcess(userInput);
        } else {
            view.displayHeader("Maaf, masukkan pilihan yang sesuai");
            mainMenuProcess();
        }
    }

    void subMenuProcess(String menu) {
        if (Objects.equals(menu, "00")) {
            if (!model.isOrderedMenuEmpty()) {
                Map<String, int[]> item = model.getMapOrderedMenu();
                String invoice = buildInvoiceFormat(item);
                view.displayOrderedMenu(invoice);
                boolean proceeded = getUserInputBool("00");
                if (proceeded) {
                    if (invoice != null) {
                        System.out.println("Pesanan anda terkonfirmasi");
                        fileHelper.writeFile(invoice);
                    } else {
                        System.out.println("Pemesanan anda dibatalkan dan anda akan kembali ke menu utama");
                        mainMenuProcess();
                    }
                }
            } else {
                view.displayHeader("Anda belum memesan menu apapun");
                mainMenuProcess();
            }
        } else {
            view.displayHeader("Pemesanan anda dibatalkan dan anda akan keluar aplikasi");
            System.exit(0);
        }
    }

    private String getMenuName(int menuId) {
        return menuList[menuId-1];
    }

    private String buildInvoiceFormat(Map<String, int[]> item) {
        StringBuilder invoice = new StringBuilder()
                .append("=".repeat(ConsoleOrderView.SEP_LENGTH))
                .append("\n")
                .append(String.format("%23s","Binar Food"))
                .append("\n")
                .append("=".repeat(ConsoleOrderView.SEP_LENGTH))
                .append("\n");

        String orderedItem = buildOrderedMenu(item).orElse("Pesanan anda kosong");
        invoice.append(orderedItem);

        int totalPrice = item.values().stream()
                .mapToInt(menuItem -> menuItem[1])
                .sum();

        invoice.append("-".repeat(ConsoleOrderView.SEP_LENGTH))
                .append("\n")
                .append("Total = ")
                .append(totalPrice)
                .append("\n");

        return invoice.toString();
    }

    private String getUserInput() {
        return inputConsole.nextLine();
    }

    private Integer getUserInputInt(Runnable code) {
        int quantity = 0;
        while (true) {
            if (inputConsole.hasNextInt()) {
                quantity = inputConsole.nextInt();
                inputConsole.nextLine();
                if (quantity == 0) {
                    return 0;
                } else {
                    return quantity;
                }
            } else {
                code.run();
            }
        }
    }

    private Boolean getUserInputBool(String caseTrue) {
        String userInput = inputConsole.nextLine();
        if (Objects.equals(userInput, caseTrue)) {
            return true;
        } else {
            return false;
        }
    }

    private Optional<String> buildOrderedMenu(Map<String, int[]> item) {
        StringBuilder orderedMenu = new StringBuilder();
        item.forEach((menuName, menuQtyAndPrice) -> orderedMenu
                .append(String.format("%-26s | %6d", menuQtyAndPrice[0] + " " + menuName, menuQtyAndPrice[1]))
                .append("\n")
        );
        return Optional.of(orderedMenu.toString());
    }
}
