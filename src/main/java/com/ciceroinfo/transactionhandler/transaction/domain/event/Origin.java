package com.ciceroinfo.transactionhandler.transaction.domain.event;

import lombok.Builder;
import lombok.Getter;
import lombok.ToString;

@Getter
@Builder
@ToString
public class Origin {
    
    private String id;
    private Integer balance;
}
