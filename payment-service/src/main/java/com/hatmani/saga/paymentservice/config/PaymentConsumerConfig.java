package com.hatmani.saga.paymentservice.config;

import com.hatmani.saga.commons.event.OrderEvent;
import com.hatmani.saga.commons.event.PaymentEvent;
import com.hatmani.saga.commons.event.Util.OrderStatus;
import com.hatmani.saga.paymentservice.service.PaymentService;
import org.reactivestreams.Publisher;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import reactor.core.publisher.Flux;
import reactor.core.publisher.Mono;

import java.util.function.Function;

@Configuration
public class PaymentConsumerConfig {
    @Autowired
    private PaymentService paymentService;

//on va consommer orderevent et on va publier paymentevent
// on peut pa utiliser consumer a la place de Function car
// elle ne permet pas l'execution de 2event

// a l'aide de spring cloud stream on va configurer d'ou cette bean read an write dans les topic
//cette configuration est dans application.yaml
    @Bean
public Function<Flux<OrderEvent>,Flux<PaymentEvent>>paymentProcessor()
{
return orderEventFlux -> orderEventFlux.flatMap(this::process);
}
//pour chaque orderevent
    private Mono<PaymentEvent> process(OrderEvent orderEvent) {
//get User id
        //Verifier le solde de user
        //if solde suff -> payment comlete et deminuer le solde
        //else payment not suffisant -> Cancel Order and Update le solde in Db

    if(OrderStatus.ORDER_CREATED.equals(orderEvent.getOrderStatus()))
    {
return  Mono.fromSupplier(()->this.paymentService.newOrderEvent(orderEvent));
    }else
        return Mono.fromRunnable(()->this.paymentService.cancelOrderEvent(orderEvent));

    }

}
