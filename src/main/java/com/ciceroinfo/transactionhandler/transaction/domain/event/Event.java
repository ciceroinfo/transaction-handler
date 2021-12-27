package com.ciceroinfo.transactionhandler.transaction.domain.event;

import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.ToString;

@Getter
@ToString
@AllArgsConstructor
public class Event {
    
    private String type;
    private String origin;
    private Integer amount;
    private String destination;
}
