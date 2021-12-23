package com.ciceroinfo.transactionhandler.transaction.domain.event;

import lombok.Builder;
import lombok.Getter;

@Getter
@Builder
public class Destination {
    
    private String id;
    private Integer balance;
    
    @Override
    public String toString() {
        return "Destination{" +
                "id='" + id + '\'' +
                ", balance=" + balance +
                '}';
    }
}
