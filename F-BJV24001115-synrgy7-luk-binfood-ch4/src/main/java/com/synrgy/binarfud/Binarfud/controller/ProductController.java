package com.synrgy.binarfud.Binarfud.controller;

import com.synrgy.binarfud.Binarfud.model.Merchant;
import com.synrgy.binarfud.Binarfud.model.Product;
import com.synrgy.binarfud.Binarfud.service.MerchantService;
import com.synrgy.binarfud.Binarfud.service.ProductService;
import jakarta.annotation.Nullable;
import lombok.extern.slf4j.Slf4j;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Component;

import java.util.List;

@Component
@Slf4j
public class ProductController {
    @Autowired
    private ProductService productService;

    @Autowired
    private MerchantService merchantService;

    public void test() {

    }

    public void createProduct(String productName, Double price, String merchantId) {
        Merchant merchant = merchantService.getMerchantById(merchantId);
        Product product = Product.builder()
                .productName(productName)
                .price(price)
                .merchant(merchant)
                .build();
        productService.insertProduct(product);
    }

    public void showAllProducts() {
        List<Product> products = productService.getAllProducts();
        products.forEach(product -> System.out.println(product.getProductName() + " | " + product.getPrice() + " | " + product.getMerchant().getMerchantName()));
    }

    public void showProductsByMerchant(String merchantId) {
        Merchant merchant = merchantService.getMerchantById(merchantId);
        List<Product> products = merchant.getProductList();
        System.out.println("Produk dari merchant " + merchant.getMerchantName());
        products.forEach(product -> System.out.println(product.getProductName() + " | " + product.getPrice()));
    }

    public void editProduct(String productId, @Nullable String newProductName, @Nullable Double newPrice) {
        Product product;
        try {
            product = productService.getProductById(productId);
            try {
                productService.updateProduct(product, newProductName, newPrice);
            } catch (RuntimeException e) {
                log.warn(e.getLocalizedMessage());
                System.out.println("Update Product \""+product.getProductName()+"\" dari merchant \""+product.getMerchant().getMerchantName()+"\" dibatalkan");
            }
        } catch (RuntimeException e) {
            log.warn(e.getLocalizedMessage());
        }
    }

    public void deleteProduct(String productId) {
        Product product;
        try {
            product = productService.getProductById(productId);
            productService.deleteProduct(product);
        } catch (RuntimeException e) {
            log.warn(e.getLocalizedMessage());
        }
    }
}
