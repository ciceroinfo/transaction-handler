package com.ciceroinfo.transactionhandler.transaction.domain.event;

import com.ciceroinfo.transactionhandler.transaction.application.event.EventInput;
import com.ciceroinfo.transactionhandler.transaction.domain.shared.AccountRepository;
import com.ciceroinfo.transactionhandler.transaction.domain.shared.Transaction;
import com.ciceroinfo.transactionhandler.transaction.domain.shared.TransactionTypes;

import java.math.BigDecimal;

/**
 * Class responsible to process only <b>Deposit</b> transactions and <i>create</i> Account when <b>Account</b> not
 * exists
 */
public class Deposit extends Transaction {
    
    public Deposit(Transaction nextTransaction) {
        super(nextTransaction);
    }
    
    @Override
    public String calc(AccountRepository cache, EventInput input) {
        
        if (TransactionTypes.DEPOSIT.equals(input.getType())) {
            
            var destination = input.getDestination();
            var amount = input.getAmount();
            
            if (cache.notExists(destination)) {
                cache.add(destination, amount.toString());
                return "creating";
            }
            
            var balance = cache.value(destination);
            
            // add to the existing amount
            cache.add(destination, new BigDecimal(balance).add(amount).toString());
            
            return "depositing";
        }
        
        return nextTransaction.calc(cache, input);
    }
}
