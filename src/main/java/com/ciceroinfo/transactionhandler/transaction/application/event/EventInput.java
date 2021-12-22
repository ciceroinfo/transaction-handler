package com.ciceroinfo.transactionhandler.transaction.application.event;

import lombok.Builder;
import lombok.Getter;

import java.math.BigDecimal;

@Getter
@Builder
public class EventInput {
    
    private String type;
    private String origin;
    private BigDecimal amount;
    private String destination;
    
    @Override
    public String toString() {
        return "EventInput{" +
                "type='" + type + '\'' +
                ", origin='" + origin + '\'' +
                ", amount=" + amount +
                ", destination='" + destination + '\'' +
                '}';
    }
}
