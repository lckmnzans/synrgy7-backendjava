package com.synrgy.binarfud.Binarfud.view;

import com.synrgy.binarfud.Binarfud.controller.MainController;
import org.springframework.stereotype.Component;

@Component
public class HomeView {
    public static final int SEP_LENGTH = 36;

    public void displayLoginMenu() {
        displayBody("Mohon login terlebih dahulu");
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
