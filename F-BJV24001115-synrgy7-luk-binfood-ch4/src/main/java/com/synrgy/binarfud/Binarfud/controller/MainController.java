package com.synrgy.binarfud.Binarfud.controller;

import com.synrgy.binarfud.Binarfud.model.Merchant;
import com.synrgy.binarfud.Binarfud.model.OrderDetail;
import com.synrgy.binarfud.Binarfud.model.Product;
import com.synrgy.binarfud.Binarfud.model.Users;
import com.synrgy.binarfud.Binarfud.view.MainView;
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
    private final MainView mainView;

    public MainController(
            MainView mainView
    ) {
        Scanner inputConsole = new Scanner(System.in);
        this.mainView = mainView;
        mainView.setScanner(inputConsole);
    }

    public void init() {
//        homeView.displayHeader("Selamat datang di Binar Food");
//        userNow = homeView.displayLoginMenu(inputConsole);
        userNow = userController.showUserDetailByUsername("lckmnzans");
        homeProcess();
    }

    public void homeProcess() {
        userInput = mainView.displayMainMenu(userNow);
        switch (userInput) {
            case "1" -> {
                List<Product> productList = productController.showAllProducts();
                orderingProcess(productList);
            }
            case "2" -> {
                List<Merchant> merchantList = merchantController.showAllMerchants(true);
                userInput = mainView.displayMerchantSelection(merchantList);
                if (userInput.equals("00")) homeProcess();
            }
            case "3" -> {
                completingOrderProcess();
            }
            default -> homeProcess();
        }
    }

    public void orderingProcess(List<Product> productList) {
        userInput = mainView.displayMenuSelection(productList);
        if (userInput.equals("00")) homeProcess();
        else {
            Product product = productController.showProduct(userInput);
            int qty = orderingSubProcess(product);
            if (qty == 0) {
                orderingProcess(productList);
            } else {
                orderController.getOrderDetailList().add(orderController.createOrderDetail(product, qty));
                orderingProcess(productList);
            }
        }
    }

    public int orderingSubProcess(Product product) {
        int qty = mainView.displayOrderingSelection(product);
        if (qty != 0) return mainView.displayOrderConfirmation(qty, product);
        return qty;
    }

    public void completingOrderProcess() {
        List<OrderDetail> orderDetailList = orderController.getOrderDetailList();
        if (!orderDetailList.isEmpty()) {
            mainView.displayHeader("Binarfud");
            orderDetailList.forEach(orderDetail ->
                    mainView.displayBody(
                            String.format("%-25s | %7.2f", orderDetail.getQuantity() + " " + orderDetail.getProduct().getProductName(), orderDetail.getTotalPrice())
                    )
            );
            int confirmation = mainView.displayCompletingOrderConfirmation();
            if (confirmation == 1) {
                orderController.createOrder(userNow.getUsername(), "", orderDetailList);
                mainView.displayHeader("Pesanan anda telah dibuat");
                orderController.clearOrderDetail();
                homeProcess();
            } else if (confirmation == 0) {
                mainView.displayHeader("Pesanan anda dibatalkan");
                orderController.clearOrderDetail();
                homeProcess();
            }
        } else {
            mainView.displayHeader("Anda belum memesan apapun");
            homeProcess();
        }
    }
}
