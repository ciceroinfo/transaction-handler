package com.ciceroinfo.transactionhandler.transaction.domain.event.type;

import com.ciceroinfo.transactionhandler.transaction.application.shared.Constants;
import com.ciceroinfo.transactionhandler.transaction.domain.event.AccountRepository;
import com.ciceroinfo.transactionhandler.transaction.domain.event.AccountResult;
import com.ciceroinfo.transactionhandler.transaction.domain.event.Event;
import com.ciceroinfo.transactionhandler.transaction.domain.event.Origin;
import com.ciceroinfo.transactionhandler.transaction.domain.shared.Transaction;
import com.ciceroinfo.transactionhandler.transaction.domain.shared.TransactionTypes;
import lombok.extern.slf4j.Slf4j;

import java.math.BigDecimal;

/**
 * Class responsible to process only <b>Withdraw</b> transactions and notify when <b>Account</b> not exists
 */
@Slf4j
public class Withdraw extends Transaction {
    
    public Withdraw(Transaction nextTransaction) {
        super(nextTransaction);
    }
    
    @Override
    public AccountResult perform(AccountRepository cache, Event event) {
        
        if (TransactionTypes.WITHDRAW.equals(event.getType())) {
            
            log.debug("Withdraw: {}", event);
            
            var origin = event.getOrigin();
            var amount = event.getAmount();
            
            if (cache.notExists(origin)) {
                return AccountResult.builder().message(Constants.NON_EXISTING_ACCOUNT).build();
            }
            
            var balance = cache.value(origin);
            
            // add to the existing amount
            cache.add(origin, BigDecimal.valueOf(balance).subtract(BigDecimal.valueOf(amount)).intValue());
            
            return result(cache, origin, "withdrawing");
        }
        
        return nextTransaction.perform(cache, event);
    }
    
    private AccountResult result(AccountRepository cache, String accountId, String message) {
        var balance = cache.value(accountId);
        var origin = Origin.builder().id(accountId).balance(balance).build();
        return AccountResult.builder().message(message).origin(origin).build();
    }
}