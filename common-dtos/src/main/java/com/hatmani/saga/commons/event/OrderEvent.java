package com.hatmani.saga.commons.event;

import com.hatmani.saga.commons.dto.OrderRequestDto;
import com.hatmani.saga.commons.event.Util.Event;
import com.hatmani.saga.commons.event.Util.OrderStatus;
import lombok.Data;
import lombok.NoArgsConstructor;

import java.util.Date;
import java.util.UUID;

@NoArgsConstructor
@Data
public class OrderEvent implements Event {
    private UUID eventId = UUID.randomUUID();
    private  Date eventDate = new Date();
private OrderRequestDto orderRequestDto;
private OrderStatus orderStatus;


    public OrderEvent(OrderRequestDto orderRequestDto, OrderStatus orderStatus) {
        this.orderRequestDto = orderRequestDto;
        this.orderStatus = orderStatus;
    }

    @Override
    public UUID getEventId() {
        return eventId;
    }

    @Override
    public Date getEventDate() {
        return eventDate;
    }
}
