package com.hatmani.saga.paymentservice.service;

import com.hatmani.saga.commons.dto.OrderRequestDto;
import com.hatmani.saga.commons.dto.PaymentRequestDto;
import com.hatmani.saga.commons.event.OrderEvent;
import com.hatmani.saga.commons.event.PaymentEvent;
import com.hatmani.saga.commons.event.Util.PaymentStatus;
import com.hatmani.saga.paymentservice.entity.UserBalance;
import com.hatmani.saga.paymentservice.entity.UserTransaction;
import com.hatmani.saga.paymentservice.repository.UserBalanceRepository;
import com.hatmani.saga.paymentservice.repository.UserTransactionRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import javax.annotation.PostConstruct;
import javax.transaction.Transactional;
import java.util.stream.Collectors;
import java.util.stream.Stream;

@Service
public class PaymentService {
    @PostConstruct
    public void initUserBalance() {
        userBalanceRepository.saveAll(Stream.of(new UserBalance(100, 3000),
                new UserBalance(101, 4000),
                new UserBalance(102, 6000),
                new UserBalance(103, 1000),
                new UserBalance(104, 25000)).collect(Collectors.toList()));
    }

    @Autowired
    private UserBalanceRepository userBalanceRepository;
    @Autowired
    private UserTransactionRepository userTransactionRepository;

    /*
    //get User id
    //Verifier le solde de user
    //if solde suff -> payment comlete et deminuer le solde
    //else payment not suffisant -> Cancel Order and Update le solde in Db

     */
    @Transactional
    public PaymentEvent newOrderEvent(OrderEvent orderEvent) {
        OrderRequestDto orderRequestDto = orderEvent.getOrderRequestDto();
        PaymentRequestDto paymentRequestDto = new PaymentRequestDto(orderRequestDto.getOrderId(),
                orderRequestDto.getUserId(), orderRequestDto.getAmount());
        PaymentEvent paymentEvent;

        userBalanceRepository.findById(orderRequestDto.getUserId())
                .filter(ub -> ub.getSolde() > orderRequestDto.getAmount())
                .map(ub -> {
                    ub.setSolde(ub.getSolde() - orderRequestDto.getAmount());
                    userTransactionRepository.save(new UserTransaction(orderRequestDto.getOrderId(),
                            orderRequestDto.getAmount(), orderRequestDto.getUserId()));
                    return new PaymentEvent(paymentRequestDto, PaymentStatus.PAYEMENT_COMPLETED);
                }).orElse(paymentEvent = new PaymentEvent(paymentRequestDto, PaymentStatus.PAYMENT_FAILED));
        return paymentEvent;
    }
@Transactional
    public void cancelOrderEvent(OrderEvent orderEvent) {
    userTransactionRepository.findById(orderEvent.getOrderRequestDto().getOrderId())
            .ifPresent(ut->{
                userTransactionRepository.delete(ut);
                userBalanceRepository.findById(ut.getUserId())
                        .ifPresent(ub->ub.setSolde(ub.getSolde()+ut.getAmount()));
            });
    }
}
