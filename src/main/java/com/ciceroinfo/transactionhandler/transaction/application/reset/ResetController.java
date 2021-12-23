package com.ciceroinfo.transactionhandler.transaction.application.reset;

import com.ciceroinfo.transactionhandler.transaction.domain.event.AccountRepository;
import lombok.extern.slf4j.Slf4j;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

@Slf4j
@RestController
@RequestMapping("/reset")
public class ResetController {
    
    @Autowired
    private AccountRepository cache;
    
    /**
     * Reset state before starting tests
     */
    @PostMapping
    public ResponseEntity<String> postReset() {
        
        log.info("resetting");
        
        cache.invalidateAll();
        
        return ResponseEntity.ok("OK");
    }
}
