package com.ciceroinfo.transactionhandler.transaction.domain.shared;

import com.ciceroinfo.transactionhandler.transaction.domain.event.AccountRepository;
import com.ciceroinfo.transactionhandler.transaction.domain.event.AccountResult;
import com.ciceroinfo.transactionhandler.transaction.domain.event.Event;

public abstract class Transaction {
    
    protected Transaction nextTransaction;
    
    /**
     * @param nextTransaction is a next transaction, case not the proper transaction
     */
    public Transaction(Transaction nextTransaction) {
        this.nextTransaction = nextTransaction;
    }
    
    /**
     * Perform the proper transaction type
     *
     * @param repository account
     * @param event      to be performed
     * @return a Transaction Account Result
     */
    public abstract AccountResult perform(AccountRepository repository, Event event);
}
