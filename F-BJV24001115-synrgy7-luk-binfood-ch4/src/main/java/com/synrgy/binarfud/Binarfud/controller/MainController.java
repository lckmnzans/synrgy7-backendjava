package com.synrgy.binarfud.Binarfud.controller;

import com.synrgy.binarfud.Binarfud.model.Merchant;
import com.synrgy.binarfud.Binarfud.model.Product;
import com.synrgy.binarfud.Binarfud.model.Users;
import com.synrgy.binarfud.Binarfud.view.HomeView;
import lombok.extern.slf4j.Slf4j;
import org.springframework.stereotype.Component;

import java.util.List;
import java.util.Scanner;

@Component
@Slf4j
public class MainController {
    private final Scanner inputConsole;
    private Users userNow;

    private final UserController userController;
    private final MerchantController merchantController;
    private final ProductController productController;
    private final OrderController orderController;
    private final HomeView homeView;

    public MainController(
            UserController userController,
            MerchantController merchantController,
            ProductController productController,
            OrderController orderController,
            HomeView homeView
    ) {
        inputConsole = new Scanner(System.in);
        this.userController = userController;
        this.merchantController = merchantController;
        this.productController = productController;
        this.orderController = orderController;
        this.homeView = homeView;
    }

    public void init() {
//        userController.test();
//        merchantController.test();
//        productController.test();
//        orderController.test();

//        homeView.displayHeader("Selamat datang di Binar Food");
//        userNow = homeView.displayLoginMenu(inputConsole);
        userNow = userController.showUserDetailByUsername("lckmnzans");
        homeView();
    }

    public void homeView() {
        String userInput = homeView.displayMainMenu(inputConsole, userNow);
        if (userInput.equals("1")) {
            List<Product> productList = productController.showAllProducts();
            homeView.displayMenuSelection(productList, inputConsole);
        } else if (userInput.equals("2")) {
            List<Merchant> merchantList = merchantController.showAllMerchants(true);
            userInput = homeView.displayMerchantSelection(merchantList, inputConsole);
            if (userInput == "00") homeView();
        }
    }
}
