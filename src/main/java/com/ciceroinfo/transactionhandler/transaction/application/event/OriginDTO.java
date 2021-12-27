package com.ciceroinfo.transactionhandler.transaction.application.event;

import com.ciceroinfo.transactionhandler.transaction.domain.event.Origin;
import lombok.Builder;
import lombok.Getter;
import lombok.ToString;

@Getter
@Builder
@ToString
public class OriginDTO {
    
    private String id;
    private Integer balance;
    
    public static OriginDTO transform(Origin origin) {
        
        if (origin == null) {
            return null;
        }
        
        return OriginDTO.builder().id(origin.getId()).balance(origin.getBalance()).build();
    }
}
