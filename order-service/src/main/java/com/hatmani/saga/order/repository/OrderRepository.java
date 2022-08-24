package com.hatmani.saga.order.repository;

import com.hatmani.saga.paymentservice.entity.PurchaseOrder;
import org.springframework.data.jpa.repository.JpaRepository;

public interface OrderRepository extends JpaRepository<PurchaseOrder,Integer> {
}
