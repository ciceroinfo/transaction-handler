package com.ciceroinfo.transactionhandler.transaction.application.event;

import com.ciceroinfo.transactionhandler.transaction.domain.event.Destination;
import lombok.Builder;
import lombok.Getter;

@Getter
@Builder
public class DestinationDTO {
    
    private String id;
    private Integer balance;
    
    public static DestinationDTO transform(Destination destination) {
        
        if (destination == null) {
            return null;
        }
        
        return DestinationDTO.builder().id(destination.getId()).balance(destination.getBalance()).build();
    }
    
    @Override
    public String toString() {
        return "Destination{" +
                "id='" + id + '\'' +
                ", balance=" + balance +
                '}';
    }
}
