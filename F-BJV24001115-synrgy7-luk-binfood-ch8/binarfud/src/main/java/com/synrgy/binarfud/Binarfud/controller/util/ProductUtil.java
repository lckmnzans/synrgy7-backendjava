package com.synrgy.binarfud.Binarfud.controller.util;

import com.synrgy.binarfud.Binarfud.model.Merchant;
import com.synrgy.binarfud.Binarfud.model.Product;
import com.synrgy.binarfud.Binarfud.service.MerchantService;
import com.synrgy.binarfud.Binarfud.service.ProductService;
import jakarta.annotation.Nullable;
import lombok.extern.slf4j.Slf4j;
import org.springframework.stereotype.Component;

import java.util.ArrayList;
import java.util.List;
import java.util.Map;

@Component
@Slf4j
public class ProductUtil {
    private final ProductService productService;

    private final MerchantService merchantService;

    public ProductUtil(ProductService productService, MerchantService merchantService) {
        this.productService = productService;
        this.merchantService = merchantService;
    }

    public Product createProduct(String productName, Double price, String merchantId) {
        Merchant merchant;
        Product product;
        try {
            merchant = merchantService.getMerchantById(merchantId);
            product = Product.builder()
                    .productName(productName)
                    .price(price)
                    .merchant(merchant)
                    .build();
            return productService.insertProduct(product);
        } catch (RuntimeException e) {
            log.error(e.getLocalizedMessage());
            throw e;
        }
    }

    public List<Product> getAllProducts(@Nullable Boolean merchantIsOpen) {
        List<Merchant> merchants;
        if (merchantIsOpen != null) {
            merchants = merchantService.getAllMerchantFilter(merchantIsOpen);
        } else {
            merchants = merchantService.getAllMerchant();
        }

        List<Product> products = new ArrayList<>();
        merchants.forEach(merchant -> products.addAll(merchant.getProductList()));
        products.forEach(product -> log.info(product.getProductName() + " | " + product.getPrice() + " | " + product.getMerchant().getMerchantName()));
        return products;
    }

    public Product getProduct(String productId) {
        Product product;
        try {
            product = productService.getProductById(productId);
            return product;
        } catch (RuntimeException e) {
            log.error(e.getLocalizedMessage());
        }
        return null;
    }

    public List<Product> getAllProductsByMerchant(String merchantId) {
        Merchant merchant = merchantService.getMerchantById(merchantId);
        return merchant.getProductList();
    }

    public Product editProduct(String productId, @Nullable String newProductName, @Nullable Double newPrice) {
        Product product;
        try {
            product = productService.getProductById(productId);
            try {
                product = productService.updateProduct(product, newProductName, newPrice);
            } catch (RuntimeException e) {
                log.error(e.getLocalizedMessage());
                System.out.println("Update Product \""+product.getProductName()+"\" dari merchant \""+product.getMerchant().getMerchantName()+"\" dibatalkan");
            }
            return product;
        } catch (RuntimeException e) {
            log.error(e.getLocalizedMessage());
            throw e;
        }
    }

    public void deleteProduct(String productId) {
        Product product;
        Merchant merchant;
        try {
            product = productService.getProductById(productId);
            merchant = product.getMerchant();
            merchant.getProductList().remove(product);
            merchantService.updateMerchant(merchant);
            productService.deleteProduct(product);
        } catch (RuntimeException e) {
            log.error(e.getLocalizedMessage());
            throw e;
        }
    }

    public List<Map<String, Object>> fetchProduct(boolean open) {
        return productService.fetchProducts(open);
    }
}
