package com.synrgy.binarfud.Binarfud.controller;

import com.synrgy.binarfud.Binarfud.model.Product;
import com.synrgy.binarfud.Binarfud.payload.ProductDto;
import com.synrgy.binarfud.Binarfud.payload.Response;
import jakarta.annotation.Nullable;
import lombok.extern.slf4j.Slf4j;
import org.modelmapper.ModelMapper;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.util.List;

@RestController
@RequestMapping("api/product")
@Slf4j
public class ProductController {
    final
    ModelMapper modelMapper;

    final
    ProductUtil productUtil;

    final
    MerchantUtil merchantUtil;

    public ProductController(ModelMapper modelMapper, ProductUtil productUtil, MerchantUtil merchantUtil) {
        this.modelMapper = modelMapper;
        this.productUtil = productUtil;
        this.merchantUtil = merchantUtil;
    }

    @GetMapping()
    public ResponseEntity<Response> getAllProducts(
            @RequestParam("id") @Nullable String merchantId,
            @RequestParam("open") @Nullable Boolean isOpen
    ) {
        if (merchantId != null) {
            return ResponseEntity.ok(getProductsByMerchant(merchantId));
        } else {
            return ResponseEntity.ok(getProductsByMerchantStatus(isOpen));
        }
    }

    private Response getProductsByMerchantStatus(@Nullable Boolean merchantIsOpen) {
        List<Product> productList = productUtil.getAllProducts(merchantIsOpen);

        List<ProductDto> productDtoList = productList.stream()
                .map(product -> modelMapper.map(product, ProductDto.class))
                .toList();
        if (!productList.isEmpty()) {
            return new Response.Success(productDtoList);
        } else {
            return new Response.Error("products are empty");
        }
    }

    private Response getProductsByMerchant(String merchantId) {
        List<Product> productList;
        try {
            productList = merchantUtil.getMerchantDetail(merchantId).getProductList();
            List<ProductDto> productDtoList = productList.stream()
                    .map(product -> modelMapper.map(product, ProductDto.class))
                    .toList();
            return new Response.Success(productDtoList);
        } catch (RuntimeException e) {
            return new Response.Error("merchant does not exist");
        }
    }

    @PostMapping("{merchantId}")
    public ResponseEntity<Response> addProduct(@PathVariable("merchantId") String merchantId, @RequestBody ProductDto productDto) {
        Product product;
        if ((productDto.getProductName() != null) && (productDto.getPrice() != null)) {
            try {
                product = productUtil.createProduct(productDto.getProductName(), productDto.getPrice(), merchantId);
                productDto = modelMapper.map(product, ProductDto.class);
                return ResponseEntity.ok(new Response.Success(productDto));
            } catch (Exception e) {
                return new ResponseEntity<>(new Response.Error(e.getLocalizedMessage()), HttpStatus.OK);
            }
        }
        return new ResponseEntity<>(new Response.Error("data belum memenuhi untuk diproses"), HttpStatus.OK);
    }

    @DeleteMapping("{id}")
    public ResponseEntity<Response> deleteProduct(@PathVariable("id") String id) {
        try {
            productUtil.deleteProduct(id);
            return ResponseEntity.ok(new Response.SuccessNull("product deleted"));
        } catch (RuntimeException e) {
            return new ResponseEntity<>(new Response.Error(e.getLocalizedMessage()), HttpStatus.OK);
        }
    }

    @PutMapping("{id}")
    public ResponseEntity<Response> update(@PathVariable("id") String id, @RequestBody ProductDto productDto) {
        Product product;
        try {
            product = modelMapper.map(productDto, Product.class);
            product = productUtil.editProduct(id, product.getProductName(), product.getPrice());
            return ResponseEntity.ok(new Response.Success(modelMapper.map(product, ProductDto.class)));
        } catch (RuntimeException e) {
            return new ResponseEntity<>(new Response.Error(e.getLocalizedMessage()), HttpStatus.OK);
        }
    }
}
