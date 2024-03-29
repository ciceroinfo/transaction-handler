package com.ciceroinfo.transactionhandler.transaction.application.event;

import com.ciceroinfo.transactionhandler.transaction.domain.event.Event;
import lombok.*;

@Getter
@Builder
@ToString
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
}
