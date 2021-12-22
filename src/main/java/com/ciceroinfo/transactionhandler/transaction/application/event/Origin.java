package com.ciceroinfo.transactionhandler.transaction.application.event;

import lombok.Builder;
import lombok.Getter;

import java.math.BigDecimal;

@Getter
@Builder
public class Origin {
    
    private String id;
    private BigDecimal balance;
    
    @Override
    public String toString() {
        return "Origin{" +
                "id='" + id + '\'' +
                ", balance=" + balance +
                '}';
    }
}
