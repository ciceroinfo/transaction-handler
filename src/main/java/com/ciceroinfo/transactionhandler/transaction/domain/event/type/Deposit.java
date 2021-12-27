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
    
    public static final String DEPOSITED = "deposited";
    public static final String CREATED = "created";
    
    public Deposit(Transaction nextTransaction) {
        super(nextTransaction);
    }
    
    /**
     * {@inheritDoc}
     */
    @Override
    public AccountResult perform(AccountRepository repository, Event event) {
        
        if (TransactionTypes.DEPOSIT.equals(event.getType())) {
            
            var destinationId = event.getDestination();
            var amount = event.getAmount();
            
            if (repository.notExists(destinationId)) {
                repository.add(destinationId, amount);
                return result(repository, destinationId, CREATED);
            }
            
            var balance = BigDecimal.valueOf(repository.value(destinationId));
            
            // add to the existing amount
            repository.add(destinationId, balance.add(BigDecimal.valueOf(amount)).intValue());
            
            return result(repository, destinationId, DEPOSITED);
        }
        
        return nextTransaction.perform(repository, event);
    }
    
    /**
     * Create an Account Result
     *
     * @param repository    account
     * @param destinationId deposit destination id
     * @param message       of the transaction result
     * @return a Transaction Account Result
     */
    private AccountResult result(AccountRepository repository, String destinationId, String message) {
        var balance = repository.value(destinationId);
        var destination = Destination.builder().id(destinationId).balance(balance).build();
        return AccountResult.builder().message(message).destination(destination).build();
    }
}
