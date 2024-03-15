package org.example;

import java.util.Arrays;
import java.util.List;
import java.util.Objects;
import java.util.Scanner;

import static org.example.Const.*;

public class Main {
    private static final Scanner inputMenu = new Scanner(System.in);
    private static Order order = null;
    private static final FileHelper fileHelper = new FileHelper();
    public static void main(String[] args) {
        String welcomeMessage =
                "=".repeat(30) +
                "\n Selamat datang di Binar Food \n" +
                "=".repeat(30);
        System.out.println(welcomeMessage);
        showMainMenu();
    }

    public static void showMainMenu() {
        order = Order.getInstance();
        
        List<String> menuList = Arrays.asList("1","2","3","4","5","6","7","8");
        List<String> subMenuOption = Arrays.asList("00","01");
        
        System.out.println(menuMessage);
        System.out.print("=> ");String userInput = inputMenu.nextLine();
        
        if (menuList.contains(userInput)) {
            showSelectedMenu(userInput);
        } else if (subMenuOption.contains(userInput)) {
            showSubMenu(userInput);
        } else {
            System.out.println("Maaf, masukkan menu atau pilihan yang sesuai! \n" + "=".repeat(30));
            showMainMenu();
        }
    }

    private static void showSubMenu(String menu) {
        if (Objects.equals(menu, "00")) {
            showOrderedMenu();
        } else {
            System.out.println("OK");
            order.clearOrder();
            System.exit(0);
        }
    }

    public static void showSelectedMenu(String menu) {
        String selectedItem = switch (menu) {
            case "1" -> "Nasi Goreng";
            case "2" -> "Mie Goreng";
            case "3" -> "Ayam Bali";
            case "4" -> "Telur Bali";
            case "5" -> "Orak-arik Ayam";
            case "6" -> "Orak-arik Telur";
            case "7" -> "Es Teh";
            case "8" -> "Es Jeruk";
            default -> "";
        };
        StringBuilder inputMsg = new StringBuilder();
        inputMsg.append("=".repeat(30))
                .append("\n")
                .append("Silahkan masukkan jumlah pesanan \n")
                .append("\n")
                .append("+ ")
                .append(selectedItem);
        System.out.println(inputMsg);
        System.out.print("qty => ");String userInput = inputMenu.nextLine();
        System.out.println("=".repeat(30));
        System.out.println("Anda memesan menu berikut"+"\n");
        System.out.println(userInput + " " + selectedItem + " | " + menuPrice[Integer.parseInt(menu) - 1] * Integer.parseInt(userInput));
        System.out.println("-".repeat(30));
        order.addOrder(menu, Integer.parseInt(userInput));

        System.out.println("=".repeat(30));
        showMainMenu();
    }

    public static void showOrderedMenu() {
        StringBuilder invoice = new StringBuilder();
        int[][] item = order.getOrders();
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
        invoice.append("-".repeat(30) + "\n" + "Total = " + totalPrice + "\n");
        System.out.println("00. Bayar pesanan");
        System.out.println("01. Batalkan pesanan");
        System.out.print("=> "); String userInput = inputMenu.nextLine();
        if (Objects.equals(userInput, "00")) {
            fileHelper.writeFile(String.valueOf(invoice));
        } else {
            System.out.println("Pesanan dibatalkan");
            System.exit(0);
        }
    }
}