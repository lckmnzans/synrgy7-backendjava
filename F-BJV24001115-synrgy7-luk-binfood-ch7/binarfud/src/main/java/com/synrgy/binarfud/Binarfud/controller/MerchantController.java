package com.synrgy.binarfud.Binarfud.controller;

import com.synrgy.binarfud.Binarfud.controller.util.MerchantUtil;
import com.synrgy.binarfud.Binarfud.model.Merchant;
import com.synrgy.binarfud.Binarfud.payload.MerchantDto;
import com.synrgy.binarfud.Binarfud.payload.Response;
import jakarta.annotation.Nullable;
import org.modelmapper.ModelMapper;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.text.ParseException;
import java.text.SimpleDateFormat;
import java.util.Date;
import java.util.LinkedHashMap;
import java.util.List;
import java.util.Map;

@RestController
@RequestMapping("api/merchant")
public class MerchantController {
    final
    ModelMapper modelMapper;

    final
    MerchantUtil merchantUtil;

    public MerchantController(MerchantUtil merchantUtil, ModelMapper modelMapper) {
        this.merchantUtil = merchantUtil;
        this.modelMapper = modelMapper;
    }

    @PostMapping()
    public ResponseEntity<Response> add(@RequestBody MerchantDto merchantDto) {
        Merchant merchant = modelMapper.map(merchantDto, Merchant.class);
        merchant = merchantUtil.createMerchant(merchant.getMerchantName(), merchant.getMerchantLocation());

        return ResponseEntity.ok(new Response.Success(merchant));
    }

    @GetMapping()
    public ResponseEntity<Response> getMerchants(@RequestParam("open") @Nullable Boolean isOpen) {
        List<Merchant> merchantList;
        if (isOpen != null) {
            merchantList = merchantUtil.getAllMerchants(isOpen);
        } else {
            merchantList = merchantUtil.getAllMerchants();
        }
        List<MerchantDto> merchantDtoList = merchantList.stream()
                .map(merchant -> modelMapper.map(merchant, MerchantDto.class))
                .toList();
        Map<String, List<MerchantDto>> data = new LinkedHashMap<>();
        data.put("merchants", merchantDtoList);
        return ResponseEntity.ok(new Response.Success(data));
    }

    @GetMapping("{id}")
    public ResponseEntity<Response> getMerchantById(@PathVariable("id") String id) {
        Merchant merchant;
        try {
            merchant = merchantUtil.getMerchantDetail(id);
            return ResponseEntity.ok(new Response.Success(modelMapper.map(merchant, MerchantDto.class)));
        } catch (RuntimeException e) {
            return new ResponseEntity<>(new Response.Error("merchant does not exist"), HttpStatus.OK);
        }
    }

    @PutMapping("{id}")
    public ResponseEntity<Response> updateOpenStatus(@PathVariable("id") String id, @RequestBody MerchantDto merchantDto) {
        Merchant merchant = modelMapper.map(merchantDto, Merchant.class);
        merchant = merchantUtil.editMerchantOpenStatus(id, merchant.isOpen());
        if (merchant != null) {
            return ResponseEntity.ok(new Response.Success(modelMapper.map(merchant, MerchantDto.class)));
        } else {
            return new ResponseEntity<>(new Response.Error("merchant does not exist"), HttpStatus.OK);
        }
    }

    @DeleteMapping("{id}")
    public ResponseEntity<Response> delete(@PathVariable("id") String id) {
        try {
            merchantUtil.deleteMerchant(id);
            return ResponseEntity.ok(new Response.SuccessNull("merchant deleted"));
        } catch (RuntimeException e) {
            return new ResponseEntity<>(new Response.Error(e.getLocalizedMessage()), HttpStatus.OK);
        }
    }

    @GetMapping("{id}/generate")
    public ResponseEntity<Response> getMerchantReport(
            @PathVariable("id") String id,
            @RequestParam("startDate") String startDate,
            @RequestParam("endDate") String endDate
    ) {
        SimpleDateFormat dateFormat = new SimpleDateFormat("dd-MM-yyyy");
        Date startPeriod;
        Date endPeriod;
        try {
            startPeriod = dateFormat.parse(startDate);
            endPeriod = dateFormat.parse(endDate);
        } catch (ParseException e) {
            return new ResponseEntity<>(new Response.Error(e.getLocalizedMessage()), HttpStatus.OK);
        }
        Double total = merchantUtil.getMerchantReport(id, startPeriod, endPeriod);

        return ResponseEntity.ok(new Response.Success(total));
    }
}
