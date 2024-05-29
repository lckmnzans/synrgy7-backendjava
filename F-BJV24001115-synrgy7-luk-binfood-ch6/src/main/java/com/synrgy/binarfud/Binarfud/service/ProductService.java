package com.synrgy.binarfud.Binarfud.service;

import com.synrgy.binarfud.Binarfud.model.Product;
import jakarta.annotation.Nullable;

import java.util.List;
import java.util.Map;

public interface ProductService {
    Product insertProduct(Product product);

    Product getProductById(String productId);

    Product updateProduct(Product product, @Nullable String newProductName, @Nullable Double newPrice);

    void deleteProduct(Product product);

    List<Product> getAllProducts();

    List<Map<String, Object>> fetchProducts(boolean open);
}
