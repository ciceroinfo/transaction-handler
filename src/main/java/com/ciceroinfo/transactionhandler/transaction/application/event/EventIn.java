package com.ciceroinfo.transactionhandler.transaction.application.event;

import com.ciceroinfo.transactionhandler.transaction.domain.event.Event;
import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Getter;
import lombok.NoArgsConstructor;

@Getter
@Builder
@NoArgsConstructor
@AllArgsConstructor
public class EventIn {
    
    private String type;
    private String origin;
    private Integer amount;
    private String destination;
    
    public Event toEvent() {
        return new Event(type, origin, amount, destination);
    }
    
    @Override
    public String toString() {
        return "EventIn{" +
                "type='" + type + '\'' +
                ", origin='" + origin + '\'' +
                ", amount=" + amount +
                ", destination='" + destination + '\'' +
                '}';
    }
}
