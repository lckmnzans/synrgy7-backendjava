package com.synrgy.binarfud.Binarfud.controller;

import com.synrgy.binarfud.Binarfud.payload.MerchantDto;
import com.synrgy.binarfud.Binarfud.model.Merchant;
import com.synrgy.binarfud.Binarfud.payload.Response;
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

    @PostMapping("merchant")
    public ResponseEntity<Response> add(@RequestBody MerchantDto merchantDto) {
        Merchant merchant = modelMapper.map(merchantDto, Merchant.class);
        merchant = merchantController.createMerchant(merchant.getMerchantName(), merchant.getMerchantLocation());

        return ResponseEntity.ok(new Response.Success(merchant));
    }

    @GetMapping("merchant")
    public ResponseEntity<Response> getMerchants(@RequestParam("open") @Nullable Boolean isOpen) {
        List<Merchant> merchantList;
        if (isOpen != null) {
            merchantList = merchantController.showAllMerchants(isOpen);
        } else {
            merchantList = merchantController.showAllMerchants();
        }
        List<MerchantDto> merchantDtoList = merchantList.stream()
                .map(merchant -> modelMapper.map(merchant, MerchantDto.class))
                .toList();
        Map<String, List<MerchantDto>> data = new LinkedHashMap<>();
        data.put("merchants", merchantDtoList);
        return ResponseEntity.ok(new Response.Success(data));
    }

    @GetMapping("merchant/{id}")
    public ResponseEntity<Response> getMerchantById(@PathVariable("id") String id) {
        Merchant merchant;
        try {
            merchant = merchantController.showMerchantDetail(id);
            return ResponseEntity.ok(new Response.Success(modelMapper.map(merchant, MerchantDto.class)));
        } catch (RuntimeException e) {
            return new ResponseEntity<>(new Response.Error("merchant does not exist"), HttpStatus.OK);
        }
    }

    @PutMapping("merchant/{id}")
    public ResponseEntity<Response> updateOpenStatus(@PathVariable("id") String id, @RequestBody MerchantDto merchantDto) {
        Merchant merchant = modelMapper.map(merchantDto, Merchant.class);
        merchant = merchantController.editMerchantsOpenStatus(id, merchant.isOpen());
        if (merchant != null) {
            return ResponseEntity.ok(new Response.Success(modelMapper.map(merchant, MerchantDto.class)));
        } else {
            return new ResponseEntity<>(new Response.Error("merchant does not exist"), HttpStatus.OK);
        }
    }

    @DeleteMapping("merchant/{id}")
    public ResponseEntity<Response> delete(@PathVariable("id") String id) {
        try {
            merchantController.deleteMerchant(id);
            return ResponseEntity.ok(new Response.SuccessNull("merchant deleted"));
        } catch (RuntimeException e) {
            return new ResponseEntity<>(new Response.Error(e.getLocalizedMessage()), HttpStatus.OK);
        }
    }
}
