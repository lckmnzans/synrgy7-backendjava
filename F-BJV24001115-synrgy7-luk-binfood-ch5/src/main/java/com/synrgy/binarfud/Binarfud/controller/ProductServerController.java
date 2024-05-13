package com.synrgy.binarfud.Binarfud.controller;

import com.synrgy.binarfud.Binarfud.model.Merchant;
import com.synrgy.binarfud.Binarfud.model.Product;
import com.synrgy.binarfud.Binarfud.payload.MerchantDto;
import com.synrgy.binarfud.Binarfud.payload.ProductDto;
import com.synrgy.binarfud.Binarfud.payload.Response;
import jakarta.annotation.Nullable;
import lombok.extern.slf4j.Slf4j;
import org.modelmapper.ModelMapper;
import org.modelmapper.TypeMap;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.util.LinkedHashMap;
import java.util.List;
import java.util.Map;

@RestController
@RequestMapping("test")
@Slf4j
public class ProductServerController {
    final
    ModelMapper modelMapper;

    final
    ProductController productController;

    final
    MerchantController merchantController;

    public ProductServerController(ModelMapper modelMapper, ProductController productController, MerchantController merchantController) {
        this.modelMapper = modelMapper;
        this.productController = productController;
        this.merchantController = merchantController;
    }

//    @GetMapping("products")
//    public ResponseEntity<Response> getAllProducts(@RequestParam("open") @Nullable Boolean merchantIsOpen) {
//        List<Product> productList = productController.showAllProducts(merchantIsOpen);
//
//        List<ProductDto> productDtoList = productList.stream()
//                .map(product -> modelMapper.map(product, ProductDto.class))
//                .toList();
//        if (!productList.isEmpty()) {
//            return ResponseEntity.ok(new Response.Success(productDtoList));
//        } else {
//            return new ResponseEntity<>(new Response.Error("products are empty"), HttpStatus.OK);
//        }
//    }
//
//    @GetMapping("products/{id}")
//    public ResponseEntity<Response> getAllProductsByMerchant(@PathVariable("id") String id) {
//        List<Product> productList;
//        try {
//             productList = merchantController.showMerchantDetail(id).getProductList();
//             List<ProductDto> productDtoList = productList.stream()
//                     .map(product -> modelMapper.map(product, ProductDto.class))
//                     .toList();
//             return ResponseEntity.ok(new Response.Success(productDtoList));
//        } catch (RuntimeException e) {
//            return new ResponseEntity<>(new Response.Error("merchant does not exist"), HttpStatus.OK);
//        }
//    }

    @GetMapping("product")
    public ResponseEntity<Response> getAllProducts(
            @RequestParam("id") @Nullable String merchantId,
            @RequestParam("open") @Nullable Boolean isOpen
    ) {
        if (merchantId != null) {
            return ResponseEntity.ok(getProductsByMerchant(merchantId));
        } else {
            return ResponseEntity.ok(getProducts(isOpen));
        }
    }

    private Response getProducts(@Nullable Boolean merchantIsOpen) {
        List<Product> productList = productController.showAllProducts(merchantIsOpen);

        List<ProductDto> productDtoList = productList.stream()
                .map(product -> modelMapper.map(product, ProductDto.class))
                .toList();
        if (!productList.isEmpty()) {
            return new Response.Success(productDtoList);
        } else {
            return new Response.Error("products are empty");
        }
    }

    private Response getProductsByMerchant(String id) {
        List<Product> productList;
        try {
            productList = merchantController.showMerchantDetail(id).getProductList();
            List<ProductDto> productDtoList = productList.stream()
                    .map(product -> modelMapper.map(product, ProductDto.class))
                    .toList();
            return new Response.Success(productDtoList);
        } catch (RuntimeException e) {
            return new Response.Error("merchant does not exist");
        }
    }

    @PostMapping("merchant/{id}")
    public ResponseEntity<Response> add(@PathVariable("id") String merchantId, @RequestBody ProductDto productDto) {
        Product product;
        if ((productDto.getProductName() != null) && (productDto.getPrice() != null)) {
            try {
                product = productController.createProduct(productDto.getProductName(), productDto.getPrice(), merchantId);
                productDto = modelMapper.map(product, ProductDto.class);
                return ResponseEntity.ok(new Response.Success(productDto));
            } catch (Exception e) {
                return new ResponseEntity<>(new Response.Error(e.getLocalizedMessage()), HttpStatus.OK);
            }
        }
        return new ResponseEntity<>(new Response.Error("data belum memenuhi untuk diproses"), HttpStatus.OK);
    }

    @DeleteMapping("product/{id}")
    public ResponseEntity<Response> delete(@PathVariable("id") String id) {
        try {
            productController.deleteProduct(id);
            return ResponseEntity.ok(new Response.SuccessNull("product deleted"));
        } catch (RuntimeException e) {
            return new ResponseEntity<>(new Response.Error(e.getLocalizedMessage()), HttpStatus.OK);
        }
    }

    @PutMapping("product/{id}")
    public ResponseEntity<Response> update(@PathVariable("id") String id, @RequestBody ProductDto productDto) {
        Product product;
        try {
            product = modelMapper.map(productDto, Product.class);
            product = productController.editProduct(id, product.getProductName(), product.getPrice());
            return ResponseEntity.ok(new Response.Success(modelMapper.map(product, ProductDto.class)));
        } catch (RuntimeException e) {
            return new ResponseEntity<>(new Response.Error(e.getLocalizedMessage()), HttpStatus.OK);
        }
    }
}