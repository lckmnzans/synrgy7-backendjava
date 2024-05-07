package com.synrgy.binarfud.Binarfud.controller;

import com.synrgy.binarfud.Binarfud.service.MerchantService;
import com.synrgy.binarfud.Binarfud.model.Merchant;
import jakarta.annotation.Nullable;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.util.HashMap;
import java.util.LinkedHashMap;
import java.util.Map;

@RestController
@RequestMapping("test")
public class WebservController {
    final
    MerchantService merchantService;

    public WebservController(MerchantService merchantService) {
        this.merchantService = merchantService;
    }

//    @GetMapping("merchant")
//    public ResponseEntity<Map<String, Object>> getMerchants() {
//        Map<String, Object> response = new LinkedHashMap<>();
//        response.put("status", "success");
//
//        Map<String, Object> data = new HashMap<>();
//        data.put("merchants", merchantService.getAllMerchant());
//
//        response.put("data", data);
//        return new ResponseEntity<>(response, HttpStatus.OK);
//    }

    @GetMapping("merchant")
    public ResponseEntity<Map<String, Object>> getMerchants(@RequestParam("open") @Nullable Boolean isOpen) {
        Map<String, Object> response = new LinkedHashMap<>();
        response.put("status", "success");

        Map<String, Object> data = new HashMap<>();
        if (isOpen != null) {
            data.put("merchants", merchantService.getAllMerchantFilter(isOpen));
        } else {
            data.put("merchants", merchantService.getAllMerchant());
        }

        response.put("data", data);
        return new ResponseEntity<>(response, HttpStatus.OK);
    }

    @PostMapping("merchant")
    public ResponseEntity<Map<String, Object>> add(@RequestBody Merchant merchant) {
        Map<String, Object> response = new LinkedHashMap<>();
        response.put("status", "success");

        Map<String, Object> data = new LinkedHashMap<>();
        data.put("merchant", merchantService.insertMerchant(merchant));
        response.put("data", data);

        return new ResponseEntity<>(response, HttpStatus.CREATED);
    }
}
