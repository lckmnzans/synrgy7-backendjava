package com.synrgy.binarfud.Binarfud.controller;

import lombok.extern.slf4j.Slf4j;
import org.springframework.stereotype.Component;

@Component
@Slf4j
public class MainController {
    private final UserController userController;
    private final MerchantController merchantController;
    private final ProductController productController;
    private final OrderController orderController;

    public MainController(UserController userController, MerchantController merchantController, ProductController productController, OrderController orderController) {
        this.userController = userController;
        this.merchantController = merchantController;
        this.productController = productController;
        this.orderController = orderController;
    }

    public void init() {
//        userController.test();
//        merchantController.test();
//        productController.test();
//        orderController.test();
        orderController.showAllOrdersByUser("lckmnzans");
    }
}
