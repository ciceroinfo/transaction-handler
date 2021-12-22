package com.ciceroinfo.transactionhandler.transaction.application.balance;

import com.ciceroinfo.transactionhandler.transaction.domain.shared.AccountRepository;
import lombok.extern.slf4j.Slf4j;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

@Slf4j
@RestController
@RequestMapping("/balance")
public class BalanceController {
    
    @Autowired
    private AccountRepository cache;
    
    @GetMapping
    @ResponseBody
    public ResponseEntity<String> getBalance(@RequestParam("account_id") String accountId) {
        
        log.info("accountId {}", accountId);
        
        if (cache.notExists(accountId)) {
            return ResponseEntity.status(HttpStatus.NOT_FOUND).body("0");
        }
        
        var balance = cache.value(accountId);
        
        return ResponseEntity.status(HttpStatus.OK).body(balance);
    }
}
