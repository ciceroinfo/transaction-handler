package com.ciceroinfo.transactionhandler.transaction.domain.event;

import lombok.Builder;
import lombok.Getter;

@Getter
@Builder
public class Origin {
    
    private String id;
    private Integer balance;
    
    @Override
    public String toString() {
        return "Origin{" +
                "id='" + id + '\'' +
                ", balance=" + balance +
                '}';
    }
}
