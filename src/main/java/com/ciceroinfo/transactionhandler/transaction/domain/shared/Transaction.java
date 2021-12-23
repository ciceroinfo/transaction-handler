package com.ciceroinfo.transactionhandler.transaction.domain.shared;

import com.ciceroinfo.transactionhandler.transaction.domain.event.AccountRepository;
import com.ciceroinfo.transactionhandler.transaction.domain.event.Event;
import com.ciceroinfo.transactionhandler.transaction.domain.event.AccountResult;

public abstract class Transaction {
    
    protected Transaction nextTransaction;
    
    public Transaction(Transaction nextTransaction) {
        this.nextTransaction = nextTransaction;
    }
    
    public abstract AccountResult perform(AccountRepository cache, Event input);
}
