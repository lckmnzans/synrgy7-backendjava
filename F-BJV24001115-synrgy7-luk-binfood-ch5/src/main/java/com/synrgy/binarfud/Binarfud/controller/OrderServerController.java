package com.synrgy.binarfud.Binarfud.controller;

import com.synrgy.binarfud.Binarfud.model.Order;
import com.synrgy.binarfud.Binarfud.model.OrderDetail;
import com.synrgy.binarfud.Binarfud.model.Users;
import com.synrgy.binarfud.Binarfud.payload.OrderDetailDto;
import com.synrgy.binarfud.Binarfud.payload.OrderDto;
import com.synrgy.binarfud.Binarfud.payload.Response;
import com.synrgy.binarfud.Binarfud.service.InvoiceService;
import com.synrgy.binarfud.Binarfud.service.JasperService;
import org.modelmapper.ModelMapper;
import org.springframework.core.io.ByteArrayResource;
import org.springframework.core.io.Resource;
import org.springframework.http.*;
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

    final
    InvoiceService invoiceService;

    public OrderServerController(ModelMapper modelMapper, OrderController orderController, UserController userController, InvoiceService invoiceService) {
        this.modelMapper = modelMapper;
        this.orderController = orderController;
        this.userController = userController;
        this.invoiceService = invoiceService;
    }

    @PostMapping("order")
    public ResponseEntity<Response> add(@RequestBody OrderDto orderDto) {
        List<OrderDetail> orderDetailList = orderDto.getOrderDetailDtoList().stream()
                .map(orderDetailDto -> orderController.createOrderDetail(orderDetailDto.getId(), orderDetailDto.getQuantity()))
                .toList();
        try {
            Order order = orderController.createOrder(orderDto.getUserName(), orderDto.getDestinationAddress(), orderDetailList);
            return ResponseEntity.ok(new Response.Success(modelMapper.map(order, OrderDto.class)));
        } catch (RuntimeException e) {
            return new ResponseEntity<>(new Response.Error(e.getLocalizedMessage()), HttpStatus.OK);
        }
    }

    @GetMapping("order/{username}")
    public ResponseEntity<Response> getAllOrders(@PathVariable("username") String username) {
        List<Order> orderDetailList = userController.getUserDetailByUsername(username).getOrderList();
        List<OrderDto> orderDtoList = orderDetailList.stream()
                .map(order -> modelMapper.map(order, OrderDto.class))
                .toList();
        return ResponseEntity.ok(new Response.Success(orderDtoList));
    }

    @GetMapping("order/page")
    public ResponseEntity<Response> getOrders(@RequestParam("pageNumber") Integer pageNumber, @RequestParam("pageAmount") Integer pageAmount) {
        List<OrderDetail> orderDetailList = orderController.getAllOrdersDetailPageable(pageNumber, pageAmount);
        List<OrderDetailDto> orderDetailDtoList = orderDetailList.stream().map(orderDetail -> modelMapper.map(orderDetail, OrderDetailDto.class)).toList();
        return ResponseEntity.ok(new Response.Success(orderDetailDtoList));
    }

    @GetMapping("order/generate/{username}")
    public ResponseEntity<Resource> getReport(@PathVariable("username") String username, @RequestParam("orderId") String orderId) {
        String userId;
        try {
            Users user = userController.getUserDetailByUsername(username);
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
