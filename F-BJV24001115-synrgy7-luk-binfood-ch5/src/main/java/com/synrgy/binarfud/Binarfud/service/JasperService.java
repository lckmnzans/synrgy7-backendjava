package com.synrgy.binarfud.Binarfud.service;

import com.synrgy.binarfud.Binarfud.model.Order;
import com.synrgy.binarfud.Binarfud.model.Users;
import com.synrgy.binarfud.Binarfud.payload.OrderDto;

import java.util.List;

public interface JasperService {
    byte[] getReport(List<Order> orderList, Users user, String format);
}
