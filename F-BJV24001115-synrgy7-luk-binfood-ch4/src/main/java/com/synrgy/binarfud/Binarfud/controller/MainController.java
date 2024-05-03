package com.synrgy.binarfud.Binarfud.controller;

import lombok.extern.slf4j.Slf4j;
import org.springframework.stereotype.Component;

@Component
@Slf4j
public class MainController {
    private final UserController userController;
    private final MerchantController merchantController;

    public MainController(UserController userController, MerchantController merchantController) {
        this.userController = userController;
        this.merchantController = merchantController;
    }

    public void init() {
        userController.init();
        merchantController.init();
    }
}
