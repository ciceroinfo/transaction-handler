package com.ciceroinfo.transactionhandler.transaction.domain.shared;

import com.ciceroinfo.transactionhandler.transaction.application.event.EventInput;

public abstract class Transaction {
    
    protected Transaction nextTransaction;
    
    public Transaction(Transaction nextTransaction) {
        this.nextTransaction = nextTransaction;
    }
    
    public abstract String calc(AccountRepository cache, EventInput input);
}
