package com.ciceroinfo.transactionhandler.transaction.application.event;

import com.ciceroinfo.transactionhandler.transaction.domain.event.Destination;
import lombok.Builder;
import lombok.Getter;
import lombok.ToString;

@Getter
@Builder
@ToString
public class DestinationDTO {
    
    private String id;
    private Integer balance;
    
    public static DestinationDTO transform(Destination destination) {
        
        if (destination == null) {
            return null;
        }
        
        return DestinationDTO.builder().id(destination.getId()).balance(destination.getBalance()).build();
    }
}
