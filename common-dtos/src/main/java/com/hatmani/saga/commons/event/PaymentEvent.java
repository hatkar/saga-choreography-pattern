package com.hatmani.saga.commons.event;

import com.hatmani.saga.commons.dto.PaymentRequestDto;
import com.hatmani.saga.commons.event.Util.Event;
import com.hatmani.saga.commons.event.Util.PaymentStatus;
import lombok.NoArgsConstructor;

import java.util.Date;
import java.util.UUID;
@NoArgsConstructor
public class PaymentEvent implements Event {
    private UUID eventId = UUID.randomUUID();
    private Date eventDate = new Date();
    private PaymentRequestDto paymentRequestDto;
    private PaymentStatus paymentStatus;

    public PaymentEvent(PaymentRequestDto paymentRequestDto, PaymentStatus paymentStatus) {
        this.paymentRequestDto = paymentRequestDto;
        this.paymentStatus = paymentStatus;
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
