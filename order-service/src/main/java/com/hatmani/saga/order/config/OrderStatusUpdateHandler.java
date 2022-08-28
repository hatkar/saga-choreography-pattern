package com.hatmani.saga.order.config;

import com.hatmani.saga.commons.dto.OrderRequestDto;
import com.hatmani.saga.commons.event.Util.OrderStatus;
import com.hatmani.saga.commons.event.Util.PaymentStatus;
import com.hatmani.saga.order.entity.PurchaseOrder;
import com.hatmani.saga.order.repository.OrderRepository;
import com.hatmani.saga.order.service.OrderStatusPublisher;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.context.annotation.Configuration;

import javax.transaction.Transactional;
import java.util.function.Consumer;

//ce class permet la mise a jour de status de l'ordre
//on le complete ou l'annuler
@Configuration
public class OrderStatusUpdateHandler {
    @Autowired
    private OrderRepository orderRepository;
    @Autowired
    private OrderStatusPublisher orderStatusPublisher;

    @Transactional
    public void updateOrder(int id,Consumer<PurchaseOrder> consumer) {
        orderRepository.findById(id).ifPresent(consumer.andThen(this::updateOrder));
    }

    private void updateOrder(PurchaseOrder purchaseOrder) {
        boolean isPaymentComplete = PaymentStatus.PAYEMENT_COMPLETED.equals(purchaseOrder.getPaymentStatus());
        OrderStatus orderStatus = isPaymentComplete ? OrderStatus.ORDER_COMPLETED : OrderStatus.ORDER_CANCELLED;
        purchaseOrder.setOrderStatus(orderStatus);
        if (!isPaymentComplete ) {
            orderStatusPublisher.publishOrderEvent(convertETtoDTO(purchaseOrder), orderStatus);
        }


    }

    public OrderRequestDto convertETtoDTO(PurchaseOrder purchaseOrder) {
        OrderRequestDto orderRequestDto = new OrderRequestDto();
        orderRequestDto.setOrderId(purchaseOrder.getId());
        orderRequestDto.setUserId(purchaseOrder.getUserId());
        orderRequestDto.setAmount(purchaseOrder.getPrice());
        orderRequestDto.setProductId(purchaseOrder.getProductId());
        return orderRequestDto;
    }
}
