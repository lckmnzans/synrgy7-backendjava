package com.synrgy.binarfud.Binarfud.service;

import com.synrgy.binarfud.Binarfud.model.Product;
import com.synrgy.binarfud.Binarfud.repository.ProductRepository;
import jakarta.annotation.Nullable;
import lombok.extern.slf4j.Slf4j;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.*;

@Service
@Slf4j
public class ProductServiceImpl implements ProductService {
    @Autowired
    private ProductRepository productRepository;

    @Override
    public Product insertProduct(Product product) {
        product = productRepository.save(product);
        log.info("Product Data successfully created");
        return product;
    }

    @Override
    public Product getProductById(String productId) {
        UUID uuid = UUID.fromString(productId);
        Optional<Product> product = productRepository.findById(uuid);
        if (product.isEmpty()) throw new RuntimeException("Product with id \""+uuid+"\" cannot be found");
        return product.get();
    }

    @Override
    public Product updateProduct(Product product, @Nullable String newProductName, @Nullable Double newPrice) {
        if (newProductName != null) product.setProductName(newProductName);
        if (newPrice != null) product.setPrice(newPrice);
        if ((newProductName == null) && (newPrice == null)) throw new RuntimeException("Update action is cancelled");
        productRepository.save(product);
        log.info("Product successfully updated");
        return product;
    }

    @Override
    public void deleteProduct(Product product) {
        productRepository.delete(product);
        log.info("Data with product name \""+product.getProductName()+"\" is successfully deleted");
    }

    @Override
    public List<Product> getAllProducts() {
        List<Product> products = productRepository.findAll();
        if (products.isEmpty()) {
            return Collections.emptyList();
        }
        return products;
    }

    @Override
    public List<Map<String, Object>> fetchProducts(boolean open) {
        return productRepository.fetchProductByMerchantStatus(open);
    }
}
