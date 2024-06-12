package com.synrgy.binarfud.Binarfud.service;

import com.synrgy.binarfud.Binarfud.model.OrderDetail;

import java.util.List;

public interface OrderDetailService {
    List<OrderDetail> createBatchesOrder(List<OrderDetail> orderDetailList);

    List<OrderDetail> getAllOrdersDetail();

    List<OrderDetail> getAllOrdersDetailPageable(int pageNumber, int pageAmount);
}
