package com.ciceroinfo.transactionhandler.transaction.domain.event;

import lombok.AllArgsConstructor;
import lombok.Getter;

@Getter
@AllArgsConstructor
public class Event {
    
    private String type;
    private String origin;
    private Integer amount;
    private String destination;
    
    @Override
    public String toString() {
        return "Event{" +
                "type='" + type + '\'' +
                ", origin='" + origin + '\'' +
                ", amount=" + amount +
                ", destination='" + destination + '\'' +
                '}';
    }
}
