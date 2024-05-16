package com.synrgy.binarfud.Binarfud.controller;

import com.synrgy.binarfud.Binarfud.model.Order;
import com.synrgy.binarfud.Binarfud.model.OrderDetail;
import com.synrgy.binarfud.Binarfud.payload.OrderDto;
import com.synrgy.binarfud.Binarfud.payload.Response;
import com.synrgy.binarfud.Binarfud.service.JasperService;
import org.modelmapper.ModelMapper;
import org.springframework.core.io.ByteArrayResource;
import org.springframework.core.io.Resource;
import org.springframework.http.*;
import org.springframework.web.bind.annotation.*;

import javax.print.attribute.standard.Media;
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
    JasperService jasperService;

    public OrderServerController(ModelMapper modelMapper, OrderController orderController, UserController userController, JasperService jasperService) {
        this.modelMapper = modelMapper;
        this.orderController = orderController;
        this.userController = userController;
        this.jasperService = jasperService;
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

    @GetMapping("order/generate/{format}")
    public ResponseEntity<Resource> getReport(@PathVariable("format") String format) {
        List<Order> orderList = orderController.getAllOrders();

        byte[] reportContent = jasperService.getReport(orderList, format);

        ByteArrayResource resource = new ByteArrayResource(reportContent);
        return ResponseEntity.ok()
                .contentType(MediaType.APPLICATION_OCTET_STREAM)
                .contentLength(resource.contentLength())
                .header(HttpHeaders.CONTENT_DISPOSITION, ContentDisposition.attachment().filename("order-list."+format).build().toString())
                .body(resource);
    }
}
