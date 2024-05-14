package com.synrgy.binarfud.Binarfud.repository;

import com.synrgy.binarfud.Binarfud.model.Product;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.stereotype.Repository;

import java.util.List;
import java.util.Map;
import java.util.UUID;

@Repository
public interface ProductRepository extends JpaRepository<Product, UUID> {
    @Query(value = "select p.id, p.product_name as productName, p.price, m.id as merchant, m.merchant_name as merchantName  from public.product p\n" +
            "inner join public.merchant m on p.merchant_id = m.id\n" +
            "where m.\"open\" =?1  and ;", nativeQuery = true)
    List<Map<String, Object>> fetchProductByMerchantStatus(boolean open);
}
