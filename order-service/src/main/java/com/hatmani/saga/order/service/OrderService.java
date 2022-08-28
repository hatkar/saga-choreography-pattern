package com.hatmani.saga.order.service;

import com.hatmani.saga.commons.dto.OrderRequestDto;
import com.hatmani.saga.commons.event.Util.OrderStatus;
import com.hatmani.saga.commons.event.Util.PaymentStatus;

import com.hatmani.saga.order.entity.PurchaseOrder;
import com.hatmani.saga.order.repository.OrderRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.util.List;

@Service
public class OrderService {
    @Autowired
    OrderRepository orderRepository;
    @Autowired
    OrderStatusPublisher orderStatusPublisher;

    @Transactional
    public PurchaseOrder createOrder(OrderRequestDto orderRequestDto) {
        PurchaseOrder order = orderRepository.save(convertDtoTOEntity(orderRequestDto));
        orderRequestDto.setOrderId(order.getId());
    //produce kafka event with status Order_Created
        orderStatusPublisher.publishOrderEvent(orderRequestDto, OrderStatus.ORDER_CREATED);


        return order;
    }

    private PurchaseOrder convertDtoTOEntity(OrderRequestDto dto) {
        return new PurchaseOrder(null, dto.getUserId(), dto.getProductId(), dto.getAmount(), OrderStatus.ORDER_CREATED, PaymentStatus.PAYMENT_INIT);
    }
    public List<PurchaseOrder> getAllOrders()
    {
        return orderRepository.findAll();
    }

}
