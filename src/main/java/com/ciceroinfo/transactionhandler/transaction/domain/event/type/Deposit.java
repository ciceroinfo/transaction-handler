package com.ciceroinfo.transactionhandler.transaction.domain.event.type;

import com.ciceroinfo.transactionhandler.transaction.domain.event.AccountRepository;
import com.ciceroinfo.transactionhandler.transaction.domain.event.AccountResult;
import com.ciceroinfo.transactionhandler.transaction.domain.event.Destination;
import com.ciceroinfo.transactionhandler.transaction.domain.event.Event;
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
    public AccountResult perform(AccountRepository cache, Event event) {
        
        if (TransactionTypes.DEPOSIT.equals(event.getType())) {
            
            var destinationId = event.getDestination();
            var amount = event.getAmount();
            
            if (cache.notExists(destinationId)) {
                cache.add(destinationId, amount);
                return result(cache, destinationId, "creating");
            }
            
            var balance = BigDecimal.valueOf(cache.value(destinationId));
            
            // add to the existing amount
            cache.add(destinationId, balance.add(BigDecimal.valueOf(amount)).intValue());
            
            return result(cache, destinationId, "depositing");
        }
        
        return nextTransaction.perform(cache, event);
    }
    
    private AccountResult result(AccountRepository cache, String destinationId, String message) {
        var balance = cache.value(destinationId);
        var destination = Destination.builder().id(destinationId).balance(balance).build();
        return AccountResult.builder().message(message).destination(destination).build();
    }
}
