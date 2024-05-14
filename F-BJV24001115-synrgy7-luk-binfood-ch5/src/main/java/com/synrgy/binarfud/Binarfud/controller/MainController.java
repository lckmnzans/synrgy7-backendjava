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
    private Scanner inputConsole;
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
        this.inputConsole = new Scanner(System.in);
        this.mainView = mainView;
        mainView.setScanner(inputConsole);
    }

    public void init() {
//        userController.test();
//        merchantController.test();
//        productController.test();
        loginProcess();
        homeProcess();
    }

    public void loginProcess() {
        mainView.displayHeader("Selamat datang di Binar Food");
        mainView.displayBody("Mohon login terlebih dahulu");
        while (true) {
            System.out.print("username :");
            String username = inputConsole.nextLine();
            System.out.print("password :");
            String password = inputConsole.nextLine();
            Users user = userController.getUserDetailByUsername(username);
            if (user != null) {
                if (user.getPassword().equals(password)) {
                    System.out.println("Login sukses");
                    userNow = user;
                    break;
                } else {
                    System.out.println("Login gagal");
                }
            }
        }
    }

    public void homeProcess() {
        userInput = mainView.displayMainMenu(userNow);
        switch (userInput) {
            case "1" -> {
                List<Product> productList = productController.showAllProducts(true);
                orderingProcess(productList);
            }
            case "2" -> {
                List<Merchant> merchantList = merchantController.showAllMerchants(true);
                userInput = mainView.displayMerchantSelection(merchantList);
                if (userInput.equals("00")) homeProcess();
                else {
                    List<Product> productList = productController.showProductsByMerchant(userInput);
                    orderingProcess(productList);
                }
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
                orderController.fillOrderDetail(product, qty);
                orderingProcess(productList);
            }
        }
    }

    public int orderingSubProcess(Product product) {
        int qty = mainView.displayOrderingSelection(product);
        if (qty != 0) {
            mainView.displayHeader("Konfirmasi menu pesanan anda");
            mainView.displayBody(String.format("%-25s | %7.1f%n", qty+" "+product.getProductName(), product.getPrice() * qty));
            int confirmation = mainView.displayConfirmation();
            if (confirmation == 1) {
                return qty;
            } else if (confirmation == 0) {
                return 0;
            }
        }
        return 0;
    }

    public void completingOrderProcess() {
        List<OrderDetail> orderDetailList = orderController.getListOfOrderDetail();
        if (!orderDetailList.isEmpty()) {
            mainView.displayHeader("Binarfud");
            orderDetailList.forEach(orderDetail ->
                    mainView.displayBody(
                            String.format("%-25s | %7.2f", orderDetail.getQuantity() + " " + orderDetail.getProduct().getProductName(), orderDetail.getTotalPrice())
                    )
            );
            int confirmation = mainView.displayConfirmation();
            if (confirmation == 1) {
                mainView.displayHeader("Masukkan destinasi pengiriman");
                mainView.displayFooter("", "=>");
                userInput = inputConsole.nextLine();
                orderController.createOrder(userNow.getUsername(), userInput, orderDetailList);
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
