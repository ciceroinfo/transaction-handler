package com.ciceroinfo.transactionhandler.transaction.domain.event.type;

import com.ciceroinfo.transactionhandler.transaction.domain.event.AccountRepository;
import com.ciceroinfo.transactionhandler.transaction.domain.event.Event;
import com.ciceroinfo.transactionhandler.transaction.domain.shared.TransactionTypes;
import org.junit.Test;
import org.junit.runner.RunWith;
import org.springframework.boot.test.mock.mockito.MockBean;
import org.springframework.test.context.junit4.SpringRunner;

import static org.junit.Assert.assertEquals;
import static org.junit.Assert.assertNotNull;
import static org.mockito.ArgumentMatchers.anyString;
import static org.mockito.Mockito.*;

@RunWith(SpringRunner.class)
public class DepositTest extends CommonTypeTest {
    
    @MockBean
    AccountRepository mockRepository;
    
    public DepositTest() {
        super(TransactionTypes.DEPOSIT, new Deposit(new End()));
    }
    
    @Test
    public void performCreateAccountWithInitialBalance() {
    
        var initialBalance = 10;
        var destinationId = "100";
        var amount = 10;
        var balance = 10;
        
        when(mockRepository.notExists(anyString())).thenReturn(Boolean.TRUE);
        when(mockRepository.value(anyString())).thenReturn(initialBalance);
        
        Event event = new Event(type, null, amount, destinationId);
        var accountResult = transaction.perform(mockRepository, event);
        
        assertNotNull(accountResult);
        assertEquals(Deposit.CREATED, accountResult.getMessage());
        
        
        verify(mockRepository, times(1)).add(destinationId, balance);
    }
    
    @Test
    public void performDepositIntoExistingAccount() {
        
        var initialBalance = 10;
        var destinationId = "100";
        var amount = 10;
        var balance = 20;
        
        when(mockRepository.notExists(anyString())).thenReturn(Boolean.FALSE);
        when(mockRepository.value(anyString())).thenReturn(initialBalance);
        
        Event event = new Event(type, null, amount, destinationId);
        var accountResult = transaction.perform(mockRepository, event);
        
        assertNotNull(accountResult);
        assertEquals(Deposit.DEPOSITED, accountResult.getMessage());
        
        verify(mockRepository, times(1)).add(destinationId, balance);
    }
}