package com.ciceroinfo.transactionhandler.transaction.application.reset;

import com.ciceroinfo.transactionhandler.transaction.domain.event.AccountRepository;
import io.swagger.annotations.Api;
import io.swagger.annotations.ApiOperation;
import io.swagger.annotations.ApiResponse;
import io.swagger.annotations.ApiResponses;
import lombok.extern.slf4j.Slf4j;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import java.util.List;

@Api(value = "reset", tags = {"reset"})
@Slf4j
@RestController
@RequestMapping("/reset")
public class ResetController {
    
    @Autowired
    private AccountRepository repository;
    
    @ApiOperation(value = "Reset state before starting tests")
    @ApiResponses(value = {
            @ApiResponse(code = 200, message = "Reset successful", response = List.class),
    })
    @PostMapping
    public ResponseEntity<String> postReset() {
        
        log.info("resetting");
        
        repository.invalidateAll();
        
        return ResponseEntity.ok("OK");
    }
}
