package com.hatmani.saga.paymentservice.controller;

import com.hatmani.saga.commons.dto.OrderRequestDto;
import com.hatmani.saga.paymentservice.entity.PurchaseOrder;
import com.hatmani.saga.paymentservice.service.OrderService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.*;

import java.util.List;

@RestController
@RequestMapping("/order")
public class OrderController {
    @Autowired
    OrderService orderService;
@PostMapping("/create")
    public PurchaseOrder createOrder(@RequestBody OrderRequestDto orderRequestDto)
    {
return orderService.createOrder(orderRequestDto);
    }

    public List<PurchaseOrder> getOrder()
    {
        return orderService.getAllOrders();
    }
}
