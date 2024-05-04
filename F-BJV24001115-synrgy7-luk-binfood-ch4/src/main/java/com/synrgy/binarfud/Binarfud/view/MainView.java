package com.synrgy.binarfud.Binarfud.view;

import com.synrgy.binarfud.Binarfud.controller.UserController;
import com.synrgy.binarfud.Binarfud.model.Merchant;
import com.synrgy.binarfud.Binarfud.model.Product;
import com.synrgy.binarfud.Binarfud.model.Users;
import lombok.extern.slf4j.Slf4j;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Component;

import java.util.*;

@Component
@Slf4j
public class MainView {
    public static final int SEP_LENGTH = 36;
    @Autowired
    private UserController userController;

    private Scanner in;

    public void setScanner(Scanner scanner) {
        this.in = scanner;
    }

    public Users displayLoginMenu() {
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

    public String displayMainMenu(Users user) {
        displayHeader("Selamat Datang "+user.getName());
        String contentBody =
                """
                        1. Lihat seluruh menu
                        2. Lihat seluruh penjual
                        3. Lihat pesanan""";
        displayBody(contentBody);
        String inputMenu;
        while (true) {
            displayFooter("00. Batalkan semuanya", "=>");
            inputMenu = in.nextLine();
            if (inputMenu.equals("1")) {
                return "1";
            } else if (inputMenu.equals("2")) {
                return "2";
            } else if (inputMenu.equals("3")) {
                return "3";
            } else if (inputMenu.equals("00")) {
                displayHeader("Pemesanan dibatalkan");
                System.exit(0);
            }
        }
    }

    public String displayMenuSelection(List<Product> productList) {
        displayHeader("Menampilkan seluruh menu");
        Map<String, Product> productHashMap = displayMenuItem(productList);
        displayBody("Masukkan nama menu untuk memesan");
        String inputMenu;
        while (true) {
            displayFooter("00. Kembali ke menu utama", "=>");
            inputMenu = in.nextLine();
            if (inputMenu.equals("00")) return "00";
            Optional<Product> product = getSelectedItem(productHashMap, inputMenu);
            if (product.isPresent()) return product.get().getId().toString();
        }
    }

    private Map<String, Product> displayMenuItem(List<Product> productList) {
        LinkedHashMap<String, Product> productLinkedHashMap = new LinkedHashMap<>();
        for (int i = 0; i<productList.size(); i++) {
            productLinkedHashMap.put(String.valueOf(i+1), productList.get(i));
            System.out.printf("%-26s | %6d%n", (i+1)+" "+productList.get(i).getProductName(), productList.get(i).getPrice().intValue());
        }
        return productLinkedHashMap;
    }

    public String displayMerchantSelection(List<Merchant> merchantList) {
        displayHeader("Menampilkan seluruh restoran");
        if (!merchantList.isEmpty()) {
            merchantList.forEach(this::displayMerchantItem);
        } else {
            displayBody("Belum ada restoran yang sedang buka");
        }
        String inputMenu;
        while (true) {
            displayFooter("00. Kembali ke menu utama", "=>");
            inputMenu = in.nextLine();
            if (inputMenu.equals("00")) return "00";
        }
    }

    private void displayMerchantItem(Merchant merchant) {
        System.out.println(merchant.getMerchantName());
        System.out.println("Lokasi : "+ merchant.getMerchantLocation());
        System.out.println("Status : "+ (merchant.isOpen() ? "buka" : "tutup"));
    }

    public int displayOrderingSelection(Product product) {
        displayHeader("Silahkan masukkan jumlah pesanan");
        displayBody("+ "+ product.getProductName());
        int inputQty = 0;
        while (true) {
            displayFooter("Masukkan 0 untuk membatalkan", "=>");
            if (in.hasNextInt()) {
                inputQty = in.nextInt();
                in.nextLine();
                if (inputQty == 0) {
                    return 0;
                } else {
                    return inputQty;
                }
            } else {
                displayHeader("Maaf, masukkan jumlah pesanan");
                displayBody("+ "+product.getProductName());
                in.nextLine();
            }
        }
    }

    public int displayOrderConfirmation(int qty, Product product) {
        displayHeader("Konfirmasi menu pesanan anda");
        String bodyContent = String.format("%-25s | %7.1f%n", qty+" "+product.getProductName(), product.getPrice() * qty);
        displayBody(bodyContent);
        String footerContent =
                """
                    00. Konfirmasi pesanan         \s
                    01. Batalkan pesanan           \s""";
        while (true) {
            displayFooter(footerContent, "=>");
            String userInput = in.nextLine();
            if (userInput.equals("00")) {
                return qty;
            } else if (userInput.equals("01")) {
                return 0;
            }
        }
    }

    public int displayCompletingOrderConfirmation() {
        String footerContent =
                """
                    00. Konfirmasi pesanan         \s
                    01. Batalkan pesanan           \s""";
        while (true) {
            displayFooter(footerContent, "=>");
            String userInput = in.nextLine();
            if (userInput.equals("00")) {
                return 1;
            } else if (userInput.equals("01")) {
                return 0;
            }
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

    private Optional<Product> getSelectedItem(Map<String, Product> productHashMap, String input) {
        if (productHashMap.containsKey(input)) return Optional.of(productHashMap.get(input));
        return Optional.empty();
    }
}