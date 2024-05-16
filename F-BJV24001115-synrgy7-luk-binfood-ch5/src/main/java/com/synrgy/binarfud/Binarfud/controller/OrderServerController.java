package com.synrgy.binarfud.Binarfud.controller;

import com.synrgy.binarfud.Binarfud.model.Order;
import com.synrgy.binarfud.Binarfud.model.OrderDetail;
import com.synrgy.binarfud.Binarfud.payload.OrderDto;
import com.synrgy.binarfud.Binarfud.payload.Response;
import org.modelmapper.ModelMapper;
import org.springframework.beans.factory.annotation.Autowired;
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

    final
    UserController userController;

    public OrderServerController(ModelMapper modelMapper, OrderController orderController, UserController userController) {
        this.modelMapper = modelMapper;
        this.orderController = orderController;
        this.userController = userController;
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

    @GetMapping("order/{username}")
    public ResponseEntity<Response> getAllOrders(@PathVariable("username") String username) {
        List<Order> orderDetailList = userController.getUserDetailByUsername(username).getOrderList();
        return null;
    }
}
