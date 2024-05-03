package com.synrgy.binarfud.Binarfud.controller;

import lombok.extern.slf4j.Slf4j;
import org.springframework.stereotype.Component;

@Component
@Slf4j
public class MainController {
    private final UserController userController;
    private final MerchantController merchantController;
    private final ProductController productController;

    public MainController(UserController userController, MerchantController merchantController, ProductController productController) {
        this.userController = userController;
        this.merchantController = merchantController;
        this.productController = productController;
    }

    public void init() {
//        userController.test();
//        merchantController.test();
        productController.test();
    }
}
