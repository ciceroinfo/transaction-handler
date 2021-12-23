package com.ciceroinfo.transactionhandler.transaction.domain.event.type;

import com.ciceroinfo.transactionhandler.transaction.domain.event.AccountRepository;
import com.ciceroinfo.transactionhandler.transaction.domain.event.Event;
import com.ciceroinfo.transactionhandler.transaction.domain.event.AccountResult;
import com.ciceroinfo.transactionhandler.transaction.domain.shared.Transaction;

/**
 * Class responsible to notify the end of transaction process or an unknown transaction type
 */
public class End extends Transaction {
    
    public End() {
        super(null);
    }
    
    @Override
    public AccountResult perform(AccountRepository cache, Event input) {
        return null;
    }
}
