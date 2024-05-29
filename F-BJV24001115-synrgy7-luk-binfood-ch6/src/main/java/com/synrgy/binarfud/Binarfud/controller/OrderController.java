package com.synrgy.binarfud.Binarfud.controller;

import com.synrgy.binarfud.Binarfud.model.Order;
import com.synrgy.binarfud.Binarfud.model.OrderDetail;
import com.synrgy.binarfud.Binarfud.model.Users;
import com.synrgy.binarfud.Binarfud.payload.OrderDetailDto;
import com.synrgy.binarfud.Binarfud.payload.OrderDto;
import com.synrgy.binarfud.Binarfud.payload.Response;
import com.synrgy.binarfud.Binarfud.service.InvoiceService;
import org.modelmapper.ModelMapper;
import org.springframework.core.io.ByteArrayResource;
import org.springframework.core.io.Resource;
import org.springframework.http.*;
import org.springframework.web.bind.annotation.*;

import java.util.List;

@RestController
@RequestMapping("api/order")
public class OrderController {

    final
    ModelMapper modelMapper;

    final
    OrderUtil orderUtil;

    final
    UserUtil userUtil;

    final
    InvoiceService invoiceService;

    public OrderController(ModelMapper modelMapper, OrderUtil orderUtil, UserUtil userUtil, InvoiceService invoiceService) {
        this.modelMapper = modelMapper;
        this.orderUtil = orderUtil;
        this.userUtil = userUtil;
        this.invoiceService = invoiceService;
    }

    @PostMapping()
    public ResponseEntity<Response> add(@RequestBody OrderDto orderDto) {
        List<OrderDetail> orderDetailList = orderDto.getOrderDetailList().stream()
                .map(orderDetailDto -> orderUtil.createOrderDetail(orderDetailDto.getId(), orderDetailDto.getQuantity()))
                .toList();
        try {
            Order order = orderUtil.createOrder(orderDto.getUserName(), orderDto.getDestinationAddress(), orderDetailList);
            return ResponseEntity.ok(new Response.Success(modelMapper.map(order, OrderDto.class)));
        } catch (RuntimeException e) {
            return new ResponseEntity<>(new Response.Error(e.getLocalizedMessage()), HttpStatus.OK);
        }
    }

    @GetMapping("{username}")
    public ResponseEntity<Response> getAllOrders(@PathVariable("username") String username) {
        List<Order> orderDetailList = userUtil.getUserDetailByUsername(username).getOrderList();
        List<OrderDto> orderDtoList = orderDetailList.stream()
                .map(order -> modelMapper.map(order, OrderDto.class))
                .toList();
        return ResponseEntity.ok(new Response.Success(orderDtoList));
    }

    @GetMapping()
    public ResponseEntity<Response> getOrderById(@RequestParam("orderId") String orderId) {
        Order order = orderUtil.getOrderDetail(orderId);
        if (order != null) {
            return ResponseEntity.ok(new Response.Success(modelMapper.map(order, OrderDto.class)));
        } else {
            return new ResponseEntity<>(new Response.Error("tidak ditemukan order sesuai id"), HttpStatus.OK);
        }
    }

    @GetMapping("page")
    public ResponseEntity<Response> getOrders(@RequestParam("pageNumber") Integer pageNumber, @RequestParam("pageAmount") Integer pageAmount) {
        List<OrderDetail> orderDetailList = orderUtil.getAllOrdersDetailPageable(pageNumber, pageAmount);
        List<OrderDetailDto> orderDetailDtoList = orderDetailList.stream().map(orderDetail -> modelMapper.map(orderDetail, OrderDetailDto.class)).toList();
        return ResponseEntity.ok(new Response.Success(orderDetailDtoList));
    }

    @GetMapping("generate/{username}")
    public ResponseEntity<Resource> getReport(@PathVariable("username") String username, @RequestParam("orderId") String orderId) {
        String userId;
        try {
            Users user = userUtil.getUserDetailByUsername(username);
            userId = user.getId().toString();
        } catch (RuntimeException e) {
            return new ResponseEntity<>(null, HttpStatus.NOT_FOUND);
        }
        byte[] reportContent = invoiceService.generateInvoice(userId, orderId);

        ByteArrayResource resource = new ByteArrayResource(reportContent);
        return ResponseEntity.ok()
                .contentType(MediaType.APPLICATION_OCTET_STREAM)
                .contentLength(resource.contentLength())
                .header(HttpHeaders.CONTENT_DISPOSITION, ContentDisposition.attachment().filename("order-list.pdf").build().toString())
                .body(resource);
    }
}
