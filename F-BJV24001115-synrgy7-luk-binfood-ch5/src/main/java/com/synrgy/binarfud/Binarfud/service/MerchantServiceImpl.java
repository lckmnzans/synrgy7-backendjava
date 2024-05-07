package com.synrgy.binarfud.Binarfud.service;

import com.synrgy.binarfud.Binarfud.model.Merchant;
import com.synrgy.binarfud.Binarfud.repository.MerchantRepository;
import lombok.extern.slf4j.Slf4j;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.Collections;
import java.util.List;
import java.util.Optional;
import java.util.UUID;

@Service
@Slf4j
public class MerchantServiceImpl implements MerchantService {
    @Autowired
    MerchantRepository merchantRepository;

    @Override
    public Merchant insertMerchant(Merchant merchant) {
        merchant = merchantRepository.save(merchant);
        log.info("Merchant Data successfully created");
        return merchant;
    }

    @Override
    public Merchant getMerchantByName(String merchantName) {
        Optional<Merchant> merchant = merchantRepository.findByMerchantName(merchantName);
        if (merchant.isEmpty()) {
            throw new RuntimeException("data with merchant_name \""+merchantName+"\" does not exist");
        }
        return merchant.get();
    }

    @Override
    public Merchant getMerchantById(String id) {
        UUID uuid = UUID.fromString(id);
        Optional<Merchant> merchant = merchantRepository.findById(uuid);
        if (merchant.isEmpty()) {
            throw new RuntimeException("data with merchant_id \""+id+"\" does not exist");
        }
        return merchant.get();
    }

    @Override
    public Merchant updateMerchant(Merchant merchant) {
        merchant = merchantRepository.save(merchant);
        return merchant;
    }

    @Override
    public List<Merchant> getAllMerchant() {
        List<Merchant> merchantList = merchantRepository.findAll();
        if (merchantList.isEmpty()) {
            return Collections.emptyList();
        }
        return merchantList;
    }

    @Override
    public List<Merchant> getAllMerchantFilter(boolean isOpen) {
        if (isOpen) {
            return merchantRepository.findMerchantsByOpenIsTrue();
        }
        return merchantRepository.findMerchantsByOpenIsFalse();
    }

    @Override
    public void hardDeleteMerchant(Merchant merchant) {
        merchantRepository.delete(merchant);
        log.info("Data with merchant name \""+merchant.getMerchantName()+"\" is successfully deleted");
    }
}
