package com.ciceroinfo.transactionhandler.transaction.domain.event;

import lombok.Builder;
import lombok.Getter;

@Getter
@Builder
public class AccountResult {
    
    private Origin origin;
    private Destination destination;
    private String message;
    
    @Override
    public String toString() {
        return "AccountResult{" +
                "origin=" + origin +
                ", destination=" + destination +
                ", message='" + message + '\'' +
                '}';
    }
}
