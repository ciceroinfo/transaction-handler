package com.ciceroinfo.transactionhandler.transaction.application;

import com.fasterxml.jackson.annotation.JsonIgnore;
import lombok.Builder;
import lombok.Getter;


@Getter
@Builder
public class EventOutput {
    @JsonIgnore
    private Origin origin;
    private Destination destination;
    
    @Override
    public String toString() {
        return "EventOutput{" +
                "origin=" + origin +
                ", destination=" + destination +
                '}';
    }
}
