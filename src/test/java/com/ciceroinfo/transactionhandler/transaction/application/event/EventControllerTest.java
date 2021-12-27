package com.ciceroinfo.transactionhandler.transaction.application.event;

import com.ciceroinfo.transactionhandler.transaction.domain.event.AccountRepository;
import com.fasterxml.jackson.databind.ObjectMapper;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.autoconfigure.web.servlet.WebMvcTest;
import org.springframework.boot.test.mock.mockito.MockBean;
import org.springframework.http.MediaType;
import org.springframework.test.web.servlet.MockMvc;

import static org.hamcrest.CoreMatchers.is;
import static org.hamcrest.Matchers.containsString;
import static org.mockito.ArgumentMatchers.anyString;
import static org.mockito.Mockito.*;
import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.post;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.*;

@WebMvcTest
public class EventControllerTest {
    
    @Autowired
    private MockMvc mockMvc;
    
    @MockBean
    private AccountRepository mockRepository;
    
    @Test
    public void depositCreateShouldReturnStatusCode201AndBodyBalance() throws Exception {
        
        when(mockRepository.notExists(anyString())).thenReturn(Boolean.TRUE);
        when(mockRepository.value(anyString())).thenReturn(10);
        
        mockMvc.perform(post("/event")
                        .content(asJsonString(new EventIn("deposit", null, 10, "100")))
                        .contentType(MediaType.APPLICATION_JSON))
                .andExpect(status().isCreated())
                .andExpect(jsonPath("$.origin").doesNotExist())
                .andExpect(jsonPath("$.destination").exists())
                .andExpect(jsonPath("$.destination.id", is("100")))
                .andExpect(jsonPath("$.destination.balance", is(10)));
        
        verify(mockRepository, times(1)).value(anyString());
        
    }
    
    @Test
    public void withdrawShouldReturnStatusCode404AndBodyZero() throws Exception {
        
        when(mockRepository.notExists(anyString())).thenReturn(Boolean.TRUE);
        when(mockRepository.value(anyString())).thenReturn(10);
        
        mockMvc.perform(post("/event")
                        .content(asJsonString(new EventIn("withdraw", "100", 10, null)))
                        .contentType(MediaType.APPLICATION_JSON))
                .andExpect(status().isNotFound())
                .andExpect(content().string(containsString("0")));
        
        verify(mockRepository, times(0)).value(anyString());
        
    }
    
    @Test
    public void withdrawShouldReturnStatusCode201AndBodyBalance() throws Exception {
        
        when(mockRepository.notExists(anyString())).thenReturn(Boolean.FALSE);
        when(mockRepository.value(anyString())).thenReturn(15);
        
        mockMvc.perform(post("/event")
                        .content(asJsonString(new EventIn("withdraw", "100", 5, null)))
                        .contentType(MediaType.APPLICATION_JSON))
                .andExpect(status().isCreated())
                .andExpect(jsonPath("$.origin").exists())
                .andExpect(jsonPath("$.destination").doesNotExist())
                .andExpect(jsonPath("$.origin.id", is("100")))
                .andExpect(jsonPath("$.origin.balance", is(15)));
        
        verify(mockRepository, times(2)).value(anyString());
        
    }
    
    public static String asJsonString(final Object obj) {
        try {
            return new ObjectMapper().writeValueAsString(obj);
        } catch (Exception e) {
            throw new RuntimeException(e);
        }
    }
}