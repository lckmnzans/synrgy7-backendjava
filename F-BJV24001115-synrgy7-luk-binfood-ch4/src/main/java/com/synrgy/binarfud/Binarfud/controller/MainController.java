package com.synrgy.binarfud.Binarfud.controller;

import com.synrgy.binarfud.Binarfud.model.Merchant;
import com.synrgy.binarfud.Binarfud.model.Product;
import com.synrgy.binarfud.Binarfud.model.Users;
import com.synrgy.binarfud.Binarfud.view.HomeView;
import lombok.extern.slf4j.Slf4j;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Component;

import java.util.List;
import java.util.Scanner;

@Component
@Slf4j
public class MainController {
    private String userInput;
    private Users userNow;

    @Autowired
    private UserController userController;
    @Autowired
    private MerchantController merchantController;
    @Autowired
    private ProductController productController;
    @Autowired
    private OrderController orderController;
    private final HomeView homeView;

    public MainController(
            HomeView homeView
    ) {
        Scanner inputConsole = new Scanner(System.in);
        this.homeView = homeView;
        homeView.setScanner(inputConsole);
    }

    public void init() {
//        homeView.displayHeader("Selamat datang di Binar Food");
//        userNow = homeView.displayLoginMenu(inputConsole);
        userNow = userController.showUserDetailByUsername("lckmnzans");
        homeView();
    }

    public void homeView() {
        userInput = homeView.displayMainMenu(userNow);
        switch (userInput) {
            case "1" -> {
                List<Product> productList = productController.showAllProducts();
                userInput = homeView.displayMenuSelection(productList);
                if (userInput.equals("00")) homeView();
                else {
                    Product product = productController.showProduct(userInput);
                    homeView.displayOrderingSelection(product);
                }
            }
            case "2" -> {
                List<Merchant> merchantList = merchantController.showAllMerchants(true);
                userInput = homeView.displayMerchantSelection(merchantList);
                if (userInput.equals("00")) homeView();
            }
            default -> homeView();
        }
    }
}
