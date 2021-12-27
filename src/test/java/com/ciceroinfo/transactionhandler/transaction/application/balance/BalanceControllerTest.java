package com.ciceroinfo.transactionhandler.transaction.application.balance;

import com.ciceroinfo.transactionhandler.transaction.domain.event.AccountRepository;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.autoconfigure.web.servlet.WebMvcTest;
import org.springframework.boot.test.mock.mockito.MockBean;
import org.springframework.test.web.servlet.MockMvc;

import static org.hamcrest.Matchers.containsString;
import static org.mockito.ArgumentMatchers.anyString;
import static org.mockito.Mockito.when;
import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.get;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.content;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.status;

@WebMvcTest
public class BalanceControllerTest {
    
    @Autowired
    private MockMvc mockMvc;
    
    @MockBean
    private AccountRepository mockRepository;
    
    @Test
    public void shouldReturnStatusCode404AndBodyZero() throws Exception {
        
        when(mockRepository.notExists(anyString())).thenReturn(Boolean.TRUE);
        
        mockMvc.perform(
                        get("/balance")
                                .param("account_id", "123"))
                .andExpect(status().isNotFound())
                .andExpect(content().string(containsString("0")));
    }
    
    @Test
    public void shouldReturnStatusCode200AndBodyWithBalance() throws Exception {
        
        when(mockRepository.notExists(anyString())).thenReturn(Boolean.FALSE);
        when(mockRepository.value(anyString())).thenReturn(100);
        
        mockMvc.perform(
                        get("/balance")
                                .param("account_id", "123"))
                .andExpect(status().isOk())
                .andExpect(content().string(containsString("100")));
    }
}