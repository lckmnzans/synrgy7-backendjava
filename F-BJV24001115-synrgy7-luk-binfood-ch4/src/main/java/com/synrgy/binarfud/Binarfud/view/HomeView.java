package com.synrgy.binarfud.Binarfud.view;

import com.synrgy.binarfud.Binarfud.controller.MainController;
import com.synrgy.binarfud.Binarfud.controller.UserController;
import com.synrgy.binarfud.Binarfud.model.Merchant;
import com.synrgy.binarfud.Binarfud.model.Product;
import com.synrgy.binarfud.Binarfud.model.Users;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Component;

import java.util.List;
import java.util.Scanner;

@Component
public class HomeView {
    public static final int SEP_LENGTH = 36;
    @Autowired
    private UserController userController;

    public Users displayLoginMenu(Scanner in) {
        displayBody("Mohon login terlebih dahulu");
        while (true) {
            System.out.print("username :");
            String username = in.nextLine();
            System.out.print("password :");
            String password = in.nextLine();
            Users user = userController.showUserDetailByUsername(username);
            if (user != null) {
                if (user.getPassword().equals(password)) {
                    System.out.println("Login sukses");
                    return user;
                } else {
                    System.out.println("Login gagal");
                }
            }
        }
    }

    public String displayMainMenu(Scanner in, Users user) {
        displayHeader("Selamat Datang "+user.getName());
        String contentBody =
                """
                        1. Lihat seluruh menu
                        2. Lihat seluruh penjual
                        
                        00. Batalkan semuanya""";
        displayBody(contentBody);
        String inputMenu;
        while (true) {
            displayFooter("", "=>");
            inputMenu = in.nextLine();
            if (inputMenu.equals("1")) {
                return "1";
            } else if (inputMenu.equals("2")) {
                return "2";
            } else if (inputMenu.equals("00")) {
                displayHeader("Pemesanan dibatalkan");
                System.exit(0);
            }
        }
    }

    public String displayMenuSelection(List<Product> productList, Scanner in) {
        displayHeader("Menampilkan seluruh menu");
        productList.forEach(product -> displayMenuItem(product));
        displayBody("Masukkan nama menu untuk memesan");
        String inputMenu;
        while (true) {
            displayFooter("00. Kembali ke menu utama", "=> ");
            inputMenu = in.nextLine();
            if (inputMenu.equals("00")) return "00";
        }
    }

    public void displayMenuItem(Product product) {
        System.out.printf("%-26s | %6d%n", "- "+product.getProductName(), product.getPrice().intValue());
    }

    public String displayMerchantSelection(List<Merchant> merchantList, Scanner in) {
        displayHeader("Menampilkan seluruh restoran");
        if (!merchantList.isEmpty()) {
            merchantList.forEach(merchant -> System.out.println(merchant.getMerchantName() + "<->" + merchant.getMerchantLocation()));
        } else {
            displayBody("Belum ada restoran yang sedang buka");
        }
        String inputMenu;
        while (true) {
            displayFooter("00. Kembali ke menu utama", "=> ");
            inputMenu = in.nextLine();
            if (inputMenu.equals("00")) return "00";
        }
    }

    public void displayHeader(String content) {
        int contentEndsAt = SEP_LENGTH - (SEP_LENGTH - content.length()) / 2;
        System.out.println("=".repeat(SEP_LENGTH));
        System.out.printf("%" + contentEndsAt + "s%n", content);
        System.out.println("=".repeat(SEP_LENGTH));
    }

    public void displayBody(String content) {
        System.out.println(content);
    }

    public void displayFooter(String content, String symbol, boolean newLine) {
        System.out.println("-".repeat(SEP_LENGTH));
        if (!content.isBlank()) {
            System.out.println(content);
        }
        System.out.print(symbol + " ");
        if (newLine) System.out.println();
    }

    public void displayFooter(String content, String symbol) {
        displayFooter(content, symbol, false);
    }
}
