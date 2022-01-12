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
    
    public static final String TRANSFERRED = "transferred";
    
    public Transfer(Transaction nextTransaction) {
        super(nextTransaction);
    }
    
    /**
     * {@inheritDoc}
     */
    @Override
    public AccountResult perform(AccountRepository repository, Event event) {
        
        if (TransactionTypes.TRANSFER.equals(event.getType())) {
            
            log.debug("Transfer: {}", event);
            
            var accountOriginId = event.getOrigin();
            
            if (repository.notExists(accountOriginId)) {
                return AccountResult.builder().message(Constants.NON_EXISTING_ACCOUNT).build();
            }
            
            var balance = BigDecimal.valueOf(repository.value(accountOriginId));
            var amount = BigDecimal.valueOf(event.getAmount());
            var accountDestinationId = event.getDestination();
            
            if (balance.compareTo(amount) < 0) {
                return AccountResult.builder().message(Constants.INSUFFICIENT_LIMIT).build();
            }
            
            // subtract amount FROM origin
            repository.add(accountOriginId, balance.subtract(amount).intValue());
            
            // transfer amount TO destination
            transferTo(repository, amount, accountDestinationId);
            
            return result(repository, accountOriginId, accountDestinationId);
        }
        
        return nextTransaction.perform(repository, event);
    }
    
    /**
     * Transfer amount to the destination account
     *
     * @param repository           account
     * @param amount               to be transferred
     * @param accountDestinationId destination id
     */
    private void transferTo(AccountRepository repository, BigDecimal amount, String accountDestinationId) {
        if (repository.notExists(accountDestinationId)) {
            repository.add(accountDestinationId, amount.intValue());
        } else {
            var accountDestinationBalance = BigDecimal.valueOf(repository.value(accountDestinationId));
            repository.add(accountDestinationId, accountDestinationBalance.add(amount).intValue());
        }
    }
    
    /**
     * Create an Account Result
     *
     * @param repository           account
     * @param accountOriginId      transfer origin id
     * @param accountDestinationId transfer destination id
     * @return a Transaction Account Result
     */
    private AccountResult result(AccountRepository repository, String accountOriginId, String accountDestinationId) {
        var accountOriginBalance = repository.value(accountOriginId);
        var accountDestinationBalance = repository.value(accountDestinationId);
        var origin = Origin.builder().id(accountOriginId).balance(accountOriginBalance).build();
        var destination = Destination.builder().id(accountDestinationId).balance(accountDestinationBalance).build();
        return AccountResult.builder().message(TRANSFERRED).origin(origin).destination(destination).build();
    }
}
