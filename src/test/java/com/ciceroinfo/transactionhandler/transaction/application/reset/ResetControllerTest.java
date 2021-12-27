package com.ciceroinfo.transactionhandler.transaction.application.reset;

import com.ciceroinfo.transactionhandler.transaction.domain.event.AccountRepository;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.autoconfigure.web.servlet.WebMvcTest;
import org.springframework.boot.test.mock.mockito.MockBean;
import org.springframework.test.web.servlet.MockMvc;

import static org.hamcrest.Matchers.containsString;
import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.post;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.content;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.status;

@WebMvcTest
public class ResetControllerTest {
    
    @Autowired
    private MockMvc mockMvc;
    
    @MockBean
    private AccountRepository mockRepository;
    
    @Test
    public void shouldReturnStatusCode200AndBodyOK() throws Exception {
        
        mockMvc.perform(post("/reset"))
                .andExpect(status().isOk())
                .andExpect(content().string(containsString("OK")));
    }
}