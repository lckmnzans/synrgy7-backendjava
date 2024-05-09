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

import java.util.List;

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

    @GetMapping("products")
    public ResponseEntity<Response> getAllProducts(@RequestParam("open") @Nullable Boolean merchantIsOpen) {
        List<Product> productList = productController.showAllProducts(merchantIsOpen);

        List<ProductDto> productDtoList = productList.stream()
                .map(product -> modelMapper.map(product, ProductDto.class))
                .toList();
        if (!productList.isEmpty()) {
            return ResponseEntity.ok(new Response.Success(productDtoList));
        } else {
            return new ResponseEntity<>(new Response.Error("products are empty"), HttpStatus.OK);
        }
    }

    @GetMapping("products/{id}")
    public ResponseEntity<Response> getAllProductsByMerchant(@PathVariable("id") String id) {
        List<Product> productList;
        try {
             productList = merchantController.showMerchantDetail(id).getProductList();
             List<ProductDto> productDtoList = productList.stream()
                     .map(product -> modelMapper.map(product, ProductDto.class))
                     .toList();
             return ResponseEntity.ok(new Response.Success(productDtoList));
        } catch (RuntimeException e) {
            return new ResponseEntity<>(new Response.Error("merchant does not exist"), HttpStatus.OK);
        }
    }
}
