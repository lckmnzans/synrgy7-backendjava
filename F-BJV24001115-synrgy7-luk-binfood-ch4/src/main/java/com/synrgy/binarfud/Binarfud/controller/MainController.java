package com.synrgy.binarfud.Binarfud.controller;

import com.synrgy.binarfud.Binarfud.view.HomeView;
import lombok.extern.slf4j.Slf4j;
import org.springframework.stereotype.Component;

import java.util.Scanner;

@Component
@Slf4j
public class MainController {
    private final Scanner inputConsole;

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
        homeView.displayHeader("Selamat datang di Binar Food");
        homeView.displayLoginMenu();
        System.out.print("username : ");
        String username = inputConsole.nextLine();
        System.out.print("password : ");
        String password = inputConsole.nextLine();
    }

    public void authLogin(String username, String password) {

    }
}
