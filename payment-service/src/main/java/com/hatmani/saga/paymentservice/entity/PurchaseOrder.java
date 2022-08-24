package com.hatmani.saga.paymentservice.entity;

import com.hatmani.saga.commons.event.Util.OrderStatus;
import com.hatmani.saga.commons.event.Util.PaymentStatus;
import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

import javax.persistence.*;
import java.io.Serializable;
@Entity
@Data
@AllArgsConstructor
@NoArgsConstructor

public class PurchaseOrder implements Serializable {
@Id
@GeneratedValue(strategy = GenerationType.IDENTITY)
    private Integer id;
private Integer userId;
private Integer productId;
private Integer price;
@Enumerated (EnumType.STRING)
private OrderStatus orderStatus;
@Enumerated (EnumType.STRING)
private PaymentStatus paymentStatus;

}
