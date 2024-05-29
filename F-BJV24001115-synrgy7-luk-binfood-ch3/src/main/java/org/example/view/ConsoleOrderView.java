package org.example.view;

import java.util.Objects;
import java.util.Scanner;

import static org.example.Const.menuList;
import static org.example.Const.menuPrice;

public class ConsoleOrderView implements OrderView {
    private final Scanner inputMenu;
    public static final int SEP_LENGTH = 36;
    public ConsoleOrderView() {
        this.inputMenu = new Scanner(System.in);
        displayHeader("Selamat datang di Binar Food");
    }

    @Override
    public void displayMainMenu() {
        displayMenuItem(menuList, menuPrice);
        String footerContent =
                """
                    00. Lihat pesanan dan bayar        \s
                    01. Keluar dan batalkan            \s""";
        displayFooter(footerContent, "=>");
    }

    @Override
    public void displaySelectedMenu(String menuId, String menuName) {
        displayHeader("Silahkan masukkan jumlah pesanan");
        displayBody("+ "+ menuName);
        displayFooter("", "=>");
    }

    @Override
    public void displayOrderedMenu(String content) {
        System.out.println(content);
        String footerContent =
                """
                    00. Bayar pesanan               \s
                    01. Batalkan pesanan            \s""";
        displayFooter(footerContent, "=>");
    }

    public boolean displayConfirmationContinue(int menuQty, String menuId, String menuName) {
        displayHeader("Konfirmasi menu pesanan anda");
        String bodyContent = String.format("%-26s | %6d", menuQty+" "+menuName, menuPrice[Integer.parseInt(menuId) - 1] * menuQty);
        displayBody(bodyContent);
        String footerContent =
                """
                    00. Konfirmasi pesanan         \s
                    01. Batalkan pesanan           \s""";
        displayFooter(footerContent, "=>");
        String userInput = inputMenu.nextLine();
        if (Objects.equals(userInput, "00")) {
            return true;
        } else {
            return false;
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

    public void displayMenuItem(String[] menuName, int[] menuPrice) {
        for (int i = 0; i < menuName.length; i++) {
            String num = String.valueOf(1+i).concat(". ");
            System.out.printf("%-26s | %6d%n", num+menuName[i], menuPrice[i]);
        }
    }
}
