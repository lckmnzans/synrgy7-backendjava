package com.synrgy.binarfud.Binarfud.controller;

import com.synrgy.binarfud.Binarfud.model.Merchant;
import com.synrgy.binarfud.Binarfud.model.Product;
import com.synrgy.binarfud.Binarfud.service.MerchantService;
import com.synrgy.binarfud.Binarfud.service.ProductService;
import jakarta.annotation.Nullable;
import lombok.extern.slf4j.Slf4j;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Component;
import org.springframework.util.StopWatch;

import java.util.List;

@Component
@Slf4j
public class ProductController {
    @Autowired
    private ProductService productService;

    @Autowired
    private MerchantService merchantService;

    public void test() {
        String merchant1 = "Geprek Jago";
        String merchant2 = "Bakmi 99 Pak Joko";
        createProduct("Geprek Bakar", 10000.0, merchant1);
        createProduct("Geprek Mozarella", 12000.0, merchant1);
        createProduct("Nasi Goreng Ayam", 12000.0, merchant2);
        createProduct("Bakmi Goreng", 14000.0, merchant2);
        createProduct("Bakmi Kuah", 14000.0, merchant2);
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

    public List<Product> showAllProducts() {
        List<Product> products = productService.getAllProducts();
        products.forEach(product -> log.info(product.getProductName() + " | " + product.getPrice() + " | " + product.getMerchant().getMerchantName()));
        return products;
    }

    public void showProductsByMerchant(String merchantId) {
        StopWatch sw = new StopWatch();
        sw.start();
        Merchant merchant = merchantService.getMerchantById(merchantId);
        List<Product> products = merchant.getProductList();
        sw.stop();
        if (sw.getTotalTimeSeconds() > 2) log.warn("Proses berjalan > 2 detik");
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
                log.error(e.getLocalizedMessage());
                System.out.println("Update Product \""+product.getProductName()+"\" dari merchant \""+product.getMerchant().getMerchantName()+"\" dibatalkan");
            }
        } catch (RuntimeException e) {
            log.error(e.getLocalizedMessage());
        }
    }

    public void deleteProduct(String productId) {
        Product product;
        try {
            product = productService.getProductById(productId);
            productService.deleteProduct(product);
        } catch (RuntimeException e) {
            log.error(e.getLocalizedMessage());
        }
    }
}
