package org.example.view;

import java.util.Objects;
import java.util.Scanner;

import static org.example.Const.*;

public class ConsoleOrderView implements OrderView {
    private Scanner inputMenu;
    public ConsoleOrderView() {
        this.inputMenu = new Scanner(System.in);
        init();
    }
    void init() {
        String welcomeMessage =
                "=".repeat(30) +
                        "\n Selamat datang di Binar Food \n" +
                        "=".repeat(30);
        System.out.println(welcomeMessage);
    }

    @Override
    public String displayMainMenu() {
        System.out.println(menuMessage);
        System.out.print("=> ");
        String userInput = inputMenu.nextLine();
        return userInput;
    }

    @Override
    public String displaySelectedMenu(String menu) {
        return switch (menu) {
            case "1" -> "Nasi Goreng";
            case "2" -> "Mie Goreng";
            case "3" -> "Ayam Bali";
            case "4" -> "Telur Bali";
            case "5" -> "Orak-arik Ayam";
            case "6" -> "Orak-arik Telur";
            case "7" -> "Es Teh";
            case "8" -> "Es Jeruk";
            default -> "0";
        };
    }

    @Override
    public int displaySelectedMenuQty(String menuId, String menuName) {
        StringBuilder askingForQtyMsg = new StringBuilder();
        askingForQtyMsg.append("=".repeat(30))
                .append("\n")
                .append("Silahkan masukkan jumlah pesanan \n")
                .append("=".repeat(30))
                .append("\n")
                .append("+ ")
                .append(menuName);
        System.out.println(askingForQtyMsg);
        System.out.print("qty => ");
        int qty = 0;
        while (true) {
            if (inputMenu.hasNextInt()) {
                qty = inputMenu.nextInt();
                inputMenu.nextLine();
                if (qty == 0) {
                    return 0;
                } else {
                    System.out.println("=".repeat(30));
                    boolean confirmation = displayConfirmationContinue(qty, menuId, menuName);
                    if (confirmation) {
                        return qty;
                    } else {
                        return 0;
                    }
                }
            } else {
                System.out.println("=".repeat(30));
                System.out.println("Mohon masukkan angka");
                System.out.println("=".repeat(30));
                System.out.print("qty => ");
                inputMenu.nextLine();
            }
        }
    }

    @Override
    public String displayOrderedMenu(int[][] item) {
        StringBuilder invoice = new StringBuilder();
        int totalPrice = 0;
        invoice.append("=".repeat(30))
                .append("\n")
                .append(" ".repeat(10))
                .append("Binar Food")
                .append(" ".repeat(10))
                .append("\n")
                .append("=".repeat(30))
                .append("\n");
        System.out.println("=".repeat(30));
        for (int i=0;i<item[0].length;i++) {
            if (item[0][i] != 0) {
                String orderedMenu = item[0][i] + " " + menuList[i] + " | " + item[1][i];
                invoice.append(orderedMenu).append("\n");
                System.out.println(orderedMenu);
                totalPrice += item[1][i];
            }
        }
        System.out.println("-".repeat(30));
        System.out.println("Total = " + totalPrice + "\n");
        invoice.append("-".repeat(30))
                .append("\n")
                .append("Total = ")
                .append(totalPrice)
                .append("\n");
        boolean confirmation = displayConfirmationOrder();
        if (confirmation) {
            return invoice.toString();
        } else {
            return null;
        }
    }

    public boolean displayConfirmationOrder() {
        System.out.println("00. Bayar pesanan");
        System.out.println("01. Batalkan pesanan");
        String userInput = inputMenu.nextLine();
        if (Objects.equals(userInput, "00")) {
            return true;
        } else {
            return false;
        }
    }

    public boolean displayConfirmationContinue(int menuQty, String menuId, String menuName) {
        System.out.println("Konfirmasi menu pesanan anda"+"\n"+"=".repeat(30));
        System.out.println(menuQty + " " + menuName + " | " + menuPrice[Integer.parseInt(menuId) - 1] * menuQty);
        System.out.println("-".repeat(30));
        System.out.println("00. Konfirmasi pesanan");
        System.out.println("01. Batalkan pesanan");
        String userInput = inputMenu.nextLine();
        if (Objects.equals(userInput, "00")) {
            return true;
        } else {
            return false;
        }
    }
}
