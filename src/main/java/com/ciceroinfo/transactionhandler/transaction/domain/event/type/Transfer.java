package com.ciceroinfo.transactionhandler.transaction.domain.event.type;

import com.ciceroinfo.transactionhandler.transaction.application.shared.Constants;
import com.ciceroinfo.transactionhandler.transaction.domain.event.*;
import com.ciceroinfo.transactionhandler.transaction.domain.shared.Transaction;
import com.ciceroinfo.transactionhandler.transaction.domain.shared.TransactionTypes;
import lombok.extern.slf4j.Slf4j;

import java.math.BigDecimal;

/**
 * Class responsible to process only <b>Withdraw</b> transactions and notify when <b>Account</b> not exists
 */
@Slf4j
public class Transfer extends Transaction {
    
    public Transfer(Transaction nextTransaction) {
        super(nextTransaction);
    }
    
    @Override
    public AccountResult perform(AccountRepository cache, Event event) {
        
        if (TransactionTypes.TRANSFER.equals(event.getType())) {
            
            log.debug("Transfer: {}", event);
            
            var accountOrigin = event.getOrigin();
            
            if (cache.notExists(accountOrigin)) {
                return AccountResult.builder().message(Constants.NON_EXISTING_ACCOUNT).build();
            }
            
            var balance = BigDecimal.valueOf(cache.value(accountOrigin));
            var amount = BigDecimal.valueOf(event.getAmount());
            var accountDestination = event.getDestination();
            
            if(amount.compareTo(balance) < 0) {
                return AccountResult.builder().message(Constants.INSUFFICIENT_LIMIT).build();
            }
    
            // Transfer FROM
            cache.add(accountOrigin, balance.subtract(amount).intValue());
    
            // Transfer TO
            transferTo(cache, amount, accountDestination);
    
            return result(cache, accountOrigin, accountDestination, "transferring");
        }
        
        return nextTransaction.perform(cache, event);
    }
    
    private void transferTo(AccountRepository cache, BigDecimal amount, String accountDestination) {
        if (cache.notExists(accountDestination)) {
            cache.add(accountDestination, amount.intValue());
        } else {
            var accountDestinationBalance = BigDecimal.valueOf(cache.value(accountDestination));
            cache.add(accountDestination, accountDestinationBalance.add(amount).intValue());
        }
    }
    
    private AccountResult result(AccountRepository cache, String accountOrigin, String accountDestination, String message) {
        var accountOriginBalance = cache.value(accountOrigin);
        var accountDestinationBalance = cache.value(accountDestination);
        var origin = Origin.builder().id(accountOrigin).balance(accountOriginBalance).build();
        var destination = Destination.builder().id(accountDestination).balance(accountDestinationBalance).build();
        return AccountResult.builder().message(message).origin(origin).destination(destination).build();
    }
}
