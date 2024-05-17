package com.synrgy.binarfud.Binarfud.service;

import com.synrgy.binarfud.Binarfud.model.Order;
import com.synrgy.binarfud.Binarfud.model.OrderDetail;
import com.synrgy.binarfud.Binarfud.model.Users;

import java.util.List;

public interface JasperService {
    byte[] generate(List<OrderDetail> orderDetailList, Users user, Order order, String format);
}
