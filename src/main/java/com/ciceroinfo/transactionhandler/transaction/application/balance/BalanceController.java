package com.ciceroinfo.transactionhandler.transaction.application.balance;

import com.ciceroinfo.transactionhandler.transaction.domain.event.AccountRepository;
import io.swagger.annotations.Api;
import io.swagger.annotations.ApiOperation;
import io.swagger.annotations.ApiResponse;
import io.swagger.annotations.ApiResponses;
import lombok.extern.slf4j.Slf4j;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.util.List;

@Api(value = "balance", tags = {"balance"})
@Slf4j
@RestController
@RequestMapping("/balance")
public class BalanceController {
    
    @Autowired
    private AccountRepository cache;
    
    @ApiOperation(value = "Retrieve the balance currently on your account")
    @ApiResponses(value = {
            @ApiResponse(code = 200, message = "successful", response = List.class),
            @ApiResponse(code = 404, message = "Account not found", response = List.class)
    })
    @GetMapping
    @ResponseBody
    public ResponseEntity<String> getBalance(@RequestParam("account_id") String accountId) {
        
        log.info("accountId {}", accountId);
        
        if (cache.notExists(accountId)) {
            return ResponseEntity.status(HttpStatus.NOT_FOUND).body("0");
        }
        
        var balance = cache.value(accountId);
        
        return ResponseEntity.status(HttpStatus.OK).body(String.valueOf(balance));
    }
}
