package com.ciceroinfo.transactionhandler.transaction.application;

import lombok.extern.slf4j.Slf4j;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.RestController;

@Slf4j
@RestController
@RequestMapping("/reset")
public class ResetController {
    
    /**
     * Reset state before starting tests
     */
    @PostMapping
    public ResponseEntity<String> postReset() {
        log.info("resetting");
        return ResponseEntity.ok("OK");
    }
}