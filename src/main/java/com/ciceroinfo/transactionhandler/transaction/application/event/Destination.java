package com.ciceroinfo.transactionhandler.transaction.application.event;

import lombok.Builder;
import lombok.Getter;

import java.math.BigDecimal;

@Getter
@Builder
public class Destination {
    
    private String id;
    private BigDecimal balance;
    
    @Override
    public String toString() {
        return "Destination{" +
                "id='" + id + '\'' +
                ", balance=" + balance +
                '}';
    }
}
