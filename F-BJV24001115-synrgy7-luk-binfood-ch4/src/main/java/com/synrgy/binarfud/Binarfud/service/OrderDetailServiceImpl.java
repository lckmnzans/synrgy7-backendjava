package com.synrgy.binarfud.Binarfud.service;

import com.synrgy.binarfud.Binarfud.model.OrderDetail;
import com.synrgy.binarfud.Binarfud.repository.OrderDetailRepository;
import lombok.extern.slf4j.Slf4j;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.PageRequest;
import org.springframework.data.domain.Pageable;
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

    @Override
    public List<OrderDetail> getAllOrdersDetail() {
        return orderDetailRepository.fetchAllOrderDetail();
    }

    @Override
    public List<OrderDetail> getAllOrdersDetailPageable(int pageNumber, int pageAmount) {
        Pageable pageable = PageRequest.of(pageNumber, pageAmount);
        Page<OrderDetail> ordersDetailPage = orderDetailRepository.findAll(pageable);
        return ordersDetailPage.toList();
    }
}
