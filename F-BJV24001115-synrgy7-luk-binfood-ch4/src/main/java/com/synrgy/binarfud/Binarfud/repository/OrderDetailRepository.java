package com.synrgy.binarfud.Binarfud.repository;

import com.synrgy.binarfud.Binarfud.model.OrderDetail;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.stereotype.Repository;

import java.util.List;
import java.util.UUID;

@Repository
public interface OrderDetailRepository extends JpaRepository<OrderDetail, UUID> {
    @Query(value = "SELECT * from order_detail od", nativeQuery = true)
    List<OrderDetail> fetchAllOrderDetail();
}
