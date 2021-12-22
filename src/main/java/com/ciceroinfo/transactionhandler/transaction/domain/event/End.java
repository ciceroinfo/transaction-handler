package com.ciceroinfo.transactionhandler.transaction.domain.event;

import com.ciceroinfo.transactionhandler.transaction.application.event.EventInput;
import com.ciceroinfo.transactionhandler.transaction.domain.shared.AccountRepository;
import com.ciceroinfo.transactionhandler.transaction.domain.shared.Transaction;

/**
 * Class responsible to notify the end of transaction process or an unknown transaction type
 */
public class End extends Transaction {
    
    public End() {
        super(null);
    }
    
    @Override
    public String calc(AccountRepository cache, EventInput input) {
        return "end";
    }
}
