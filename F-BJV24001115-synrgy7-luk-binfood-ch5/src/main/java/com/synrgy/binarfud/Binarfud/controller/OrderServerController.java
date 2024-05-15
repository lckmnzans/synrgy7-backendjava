package com.synrgy.binarfud.Binarfud.controller;

import com.synrgy.binarfud.Binarfud.model.Order;
import com.synrgy.binarfud.Binarfud.model.OrderDetail;
import com.synrgy.binarfud.Binarfud.payload.OrderDto;
import com.synrgy.binarfud.Binarfud.payload.Response;
import org.modelmapper.ModelMapper;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.util.List;

@RestController
@RequestMapping("api")
public class OrderServerController {

    final
    ModelMapper modelMapper;

    final
    OrderController orderController;

    public OrderServerController(ModelMapper modelMapper, OrderController orderController) {
        this.modelMapper = modelMapper;
        this.orderController = orderController;
    }

    @PostMapping("order")
    public ResponseEntity<Response> add(@RequestBody OrderDto orderDto) {
        List<OrderDetail> orderDetailList = orderDto.getOrderDetailDtoList().stream()
                .map(orderDetailDto -> orderController.createOrderDetail(orderDetailDto.getId(), orderDetailDto.getQuantity()))
                .toList();

        try {
            orderController.createOrder(orderDto.getUserName(), orderDto.getDestinationAddress(), orderDetailList);
            return ResponseEntity.ok(new Response.SuccessNull("order sukses dibuat"));
        } catch (RuntimeException e) {
            return new ResponseEntity<>(new Response.Error(e.getLocalizedMessage()), HttpStatus.OK);
        }
    }
}
