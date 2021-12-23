package com.ciceroinfo.transactionhandler.transaction.application.event;

import com.ciceroinfo.transactionhandler.transaction.domain.event.AccountResult;
import com.fasterxml.jackson.annotation.JsonInclude;
import lombok.Getter;


@Getter
@JsonInclude(JsonInclude.Include.NON_NULL)
public class EventOut {
    
    private final OriginDTO origin;
    private final DestinationDTO destination;
    
    public EventOut(AccountResult accountResult) {
        origin = OriginDTO.transform(accountResult.getOrigin());
        destination = DestinationDTO.transform(accountResult.getDestination());
    }
    
    @Override
    public String toString() {
        return "EventOut{" +
                "origin=" + origin +
                ", destination=" + destination +
                '}';
    }
}
