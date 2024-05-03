package com.synrgy.binarfud.Binarfud.service;

import com.synrgy.binarfud.Binarfud.model.OrderDetail;
import com.synrgy.binarfud.Binarfud.repository.OrderDetailRepository;
import lombok.extern.slf4j.Slf4j;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.List;

@Service
@Slf4j
public class OrderDetailServiceImpl implements OrderDetailService {
    @Autowired
    private OrderDetailRepository orderDetailRepository;

    @Override
    public void createBatchesOrder(List<OrderDetail> orderDetailList) {
        orderDetailRepository.saveAll(orderDetailList);
        log.info("All OrderDetail is successfully created");
    }
}
