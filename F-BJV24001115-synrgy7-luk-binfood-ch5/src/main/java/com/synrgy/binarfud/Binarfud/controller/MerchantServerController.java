package com.synrgy.binarfud.Binarfud.controller;

import com.synrgy.binarfud.Binarfud.payload.MerchantDto;
import com.synrgy.binarfud.Binarfud.model.Merchant;
import jakarta.annotation.Nullable;
import lombok.extern.slf4j.Slf4j;
import org.modelmapper.ModelMapper;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.util.*;

@RestController
@RequestMapping("test")
@Slf4j
public class MerchantServerController {
    final
    ModelMapper modelMapper;

    final
    MerchantController merchantController;

    public MerchantServerController(MerchantController merchantController, ModelMapper modelMapper) {
        this.merchantController = merchantController;
        this.modelMapper = modelMapper;
    }

    @GetMapping("merchant")
    public ResponseEntity<Map<String, Object>> getMerchants(@RequestParam("open") @Nullable Boolean isOpen) {
        Map<String, Object> response = new LinkedHashMap<>();
        response.put("status", "success");

        Map<String, Object> data = new HashMap<>();
        List<Merchant> merchantList;
        if (isOpen != null) {
            merchantList = merchantController.showAllMerchants(isOpen);
        } else {
            merchantList = merchantController.showAllMerchants();
        }
        List<MerchantDto> merchantDtoList = merchantList.stream().map(merchant -> modelMapper.map(merchant, MerchantDto.class)).toList();
        data.put("merchant", merchantDtoList);

        response.put("data", data);
        return new ResponseEntity<>(response, HttpStatus.OK);
    }

    @PostMapping("merchant")
    public ResponseEntity<Map<String, Object>> add(@RequestBody MerchantDto merchantDto) {
        Map<String, Object> response = new LinkedHashMap<>();
        response.put("status", "success");

        Map<String, Object> data = new LinkedHashMap<>();
        Merchant merchant = modelMapper.map(merchantDto, Merchant.class);
        data.put("merchant", merchantController.createMerchant(merchant.getMerchantName(), merchant.getMerchantLocation()));
        response.put("data", data);

        return new ResponseEntity<>(response, HttpStatus.CREATED);
    }

    @PutMapping("merchant/{id}")
    public ResponseEntity<Map<String, Object>> update(@PathVariable("id") String id, @RequestBody MerchantDto merchantDto) {
        Map<String, Object> response = new LinkedHashMap<>();
        Map<String, Object> data = new HashMap<>();

        Merchant merchant = modelMapper.map(merchantDto, Merchant.class);
        merchant = merchantController.editMerchantsOpenStatus(id, merchant.isOpen());
        if (merchant == null) {
            response.put("status", "failed");
        } else {
            response.put("status", "success");
            data.put("merchant", modelMapper.map(merchant, MerchantDto.class));
        }
        response.put("data", data);
        return new ResponseEntity<>(response, HttpStatus.OK);
    }
}
