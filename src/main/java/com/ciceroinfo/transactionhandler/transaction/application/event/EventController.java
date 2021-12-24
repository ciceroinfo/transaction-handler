package com.ciceroinfo.transactionhandler.transaction.application.event;

import com.ciceroinfo.transactionhandler.transaction.application.shared.Constants;
import com.ciceroinfo.transactionhandler.transaction.domain.event.AccountRepository;
import com.ciceroinfo.transactionhandler.transaction.domain.event.AccountResult;
import com.ciceroinfo.transactionhandler.transaction.domain.event.type.Deposit;
import com.ciceroinfo.transactionhandler.transaction.domain.event.type.End;
import com.ciceroinfo.transactionhandler.transaction.domain.event.type.Transfer;
import com.ciceroinfo.transactionhandler.transaction.domain.event.type.Withdraw;
import com.ciceroinfo.transactionhandler.transaction.domain.shared.Transaction;
import io.swagger.annotations.Api;
import io.swagger.annotations.ApiOperation;
import io.swagger.annotations.ApiResponse;
import io.swagger.annotations.ApiResponses;
import lombok.extern.slf4j.Slf4j;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;
import org.springframework.web.servlet.support.ServletUriComponentsBuilder;

import javax.annotation.PostConstruct;
import java.util.List;

@Api(value = "event", tags = {"event"})
@Slf4j
@RestController
@RequestMapping("/event")
public class EventController {
    
    @Autowired
    private AccountRepository cache;
    
    private Transaction transaction;
    
    @PostConstruct
    public void init() {
        // Types of transactions allowed
        transaction = new Deposit(new Withdraw(new Transfer(new End())));
    }
    
    @ApiOperation(value = "Retrieve the balance currently on your account")
    @ApiResponses(value = {
            @ApiResponse(code = 200, message = "Successfully processed", response = List.class),
            @ApiResponse(code = 404, message = "Account not found", response = List.class)
    })
    @PostMapping
    public ResponseEntity<Object> postEvent(@RequestBody EventIn eventIn) {
        
        log.debug("eventIn {}", eventIn);
        
        var event = eventIn.toEvent();
        
        var accountResult = transaction.perform(cache, event);
        
        if (Constants.NON_EXISTING_ACCOUNT.equals(accountResult.getMessage())) {
            return ResponseEntity.status(HttpStatus.NOT_FOUND).body("0");
        }
        
        return accountResponse(accountResult);
    }
    
    /**
     * Create an Event Out response
     *
     * @param accountResult with the transaction result
     * @return Event Out response
     */
    private ResponseEntity<Object> accountResponse(AccountResult accountResult) {
        var eventOut = new EventOut(accountResult);
        
        var uri = ServletUriComponentsBuilder.fromCurrentRequest()
                .build()
                .toUri();
        log.debug("eventOut {}", eventOut);
        return ResponseEntity.created(uri).body(eventOut);
    }
}
