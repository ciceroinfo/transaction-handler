package com.ciceroinfo.transactionhandler.transaction.domain.event;

import com.ciceroinfo.transactionhandler.transaction.application.shared.Constants;
import com.ciceroinfo.transactionhandler.transaction.application.event.EventInput;
import com.ciceroinfo.transactionhandler.transaction.domain.shared.AccountRepository;
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
    public String calc(AccountRepository cache, EventInput input) {
        
        if (TransactionTypes.WITHDRAW.equals(input.getType())) {
            
            log.debug("Withdraw: {}", input);
            
            var origin = input.getOrigin();
            var amount = input.getAmount();
            
            if (cache.notExists(origin)) {
                return Constants.NON_EXISTING_ACCOUNT;
            }
            
            var balance = cache.value(origin);
            
            // add to the existing amount
            cache.add(origin, new BigDecimal(balance).subtract(amount).toString());
            
            log.debug("Final balance: {}", cache.value(origin));
            
            return "withdrawing";
        }
        
        return nextTransaction.calc(cache, input);
    }
}
