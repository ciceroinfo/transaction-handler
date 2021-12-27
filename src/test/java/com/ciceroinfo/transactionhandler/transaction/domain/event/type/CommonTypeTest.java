package com.ciceroinfo.transactionhandler.transaction.domain.event.type;

import com.ciceroinfo.transactionhandler.transaction.domain.shared.Transaction;

public abstract class CommonTypeTest {
    
    String type;
    Transaction transaction;
    
    CommonTypeTest(String type, Transaction transaction) {
        this.type = type;
        this.transaction = transaction;
    }
}
