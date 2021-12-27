package com.ciceroinfo.transactionhandler.transaction.domain.event.type;

import com.ciceroinfo.transactionhandler.transaction.application.shared.Constants;
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
public class TransferTest extends CommonTypeTest {
    
    @MockBean
    AccountRepository mockRepository;
    
    public TransferTest() {
        super(TransactionTypes.TRANSFER, new Transfer(new End()));
    }
    
    @Test
    public void performWithdrawFromNonExistingAccount() {
        
        var originId = "100";
        var amount = 10;
        var balance = 10;
        
        when(mockRepository.notExists(anyString())).thenReturn(Boolean.TRUE);
        
        Event event = new Event(type, originId, amount, null);
        var accountResult = transaction.perform(mockRepository, event);
        
        assertNotNull(accountResult);
        assertEquals(Constants.NON_EXISTING_ACCOUNT, accountResult.getMessage());
        
        verify(mockRepository, times(0)).add(originId, balance);
    }
    
    @Test
    public void performWithdrawFromExistingAccount() {
        
        var initialBalance = 10;
        var originId = "100";
        var destinationId = "300";
        var amount = 10;
        var balance = 0;
        
        when(mockRepository.notExists(anyString())).thenReturn(Boolean.FALSE);
        when(mockRepository.value(anyString())).thenReturn(initialBalance);
        
        Event event = new Event(type, originId, amount, destinationId);
        var accountResult = transaction.perform(mockRepository, event);
        
        assertNotNull(accountResult);
        assertEquals(Transfer.TRANSFERRED, accountResult.getMessage());
        
        verify(mockRepository, times(1)).add(originId, balance);
    }
}