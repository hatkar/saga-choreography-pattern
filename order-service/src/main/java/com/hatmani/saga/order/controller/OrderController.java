package com.hatmani.saga.order.controller;

import com.hatmani.saga.commons.dto.OrderRequestDto;
import com.hatmani.saga.order.entity.PurchaseOrder;
import com.hatmani.saga.order.service.OrderService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import java.util.List;

@RestController
@RequestMapping("/order")
public class OrderController {
    @Autowired
    OrderService orderService;

    @PostMapping("/create")
    public PurchaseOrder createOrder(@RequestBody OrderRequestDto orderRequestDto) {
        return orderService.createOrder(orderRequestDto);
    }

    public List<PurchaseOrder> getOrder() {
        return orderService.getAllOrders();
    }
}
