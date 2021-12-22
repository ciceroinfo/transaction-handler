package com.ciceroinfo.transactionhandler.transaction.application.event;

import lombok.Builder;
import lombok.Getter;


@Getter
@Builder
public class EventOutput {
    
    private Origin origin;
    private Destination destination;
    
    @Override
    public String toString() {
        return "EventOutput{" +
                "origin=" + origin +
                ", destination=" + destination +
                '}';
    }
}
