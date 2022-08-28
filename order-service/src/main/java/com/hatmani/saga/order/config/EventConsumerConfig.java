package com.hatmani.saga.order.config;

import com.hatmani.saga.commons.event.PaymentEvent;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;

import java.util.function.Consumer;
//ce bean se declanche et lit de topic le message envoyer par le microservice payment
@Configuration
public class EventConsumerConfig {
    @Autowired
private OrderStatusUpdateHandler handler;
    @Bean
    public Consumer<PaymentEvent> paymentEventConsummer(){
        //listen payment-event-topic
        //check les status de payment
        //si completed alors on complete l'ordre
        //sino annuler l'order
        return (payment)->handler.updateOrder(payment.getPaymentRequestDto().getOrderId(),purchaseOrder -> {
            purchaseOrder.setPaymentStatus(payment.getPaymentStatus());
        });
    }
}
