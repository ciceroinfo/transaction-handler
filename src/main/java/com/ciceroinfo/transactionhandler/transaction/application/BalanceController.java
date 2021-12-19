package com.ciceroinfo.transactionhandler.transaction.application;

import lombok.extern.slf4j.Slf4j;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

@Slf4j
@RestController
@RequestMapping("/balance")
public class BalanceController {
    
    @GetMapping
    @ResponseBody
    public ResponseEntity<String> getBalance(@RequestParam("account_id") long accountId) {
        log.info("accountId {}", accountId);
        return ResponseEntity.status(HttpStatus.NOT_FOUND).body("0");
    }
}
