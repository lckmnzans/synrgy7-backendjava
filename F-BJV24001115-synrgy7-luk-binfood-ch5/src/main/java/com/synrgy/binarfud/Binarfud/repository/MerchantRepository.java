package com.synrgy.binarfud.Binarfud.repository;

import com.synrgy.binarfud.Binarfud.model.Merchant;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

import java.util.List;
import java.util.Optional;
import java.util.UUID;

@Repository
public interface MerchantRepository extends JpaRepository<Merchant, UUID> {
    Optional<Merchant> findByMerchantName(String merchantName);

    List<Merchant> findMerchantsByOpenIsTrue();

    List<Merchant> findMerchantsByOpenIsFalse();
}
