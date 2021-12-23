package com.ciceroinfo.transactionhandler.transaction.application.event;

import com.ciceroinfo.transactionhandler.transaction.application.shared.Constants;
import com.ciceroinfo.transactionhandler.transaction.domain.event.AccountRepository;
import com.ciceroinfo.transactionhandler.transaction.domain.event.type.Deposit;
import com.ciceroinfo.transactionhandler.transaction.domain.event.type.End;
import com.ciceroinfo.transactionhandler.transaction.domain.event.type.Transfer;
import com.ciceroinfo.transactionhandler.transaction.domain.event.type.Withdraw;
import com.ciceroinfo.transactionhandler.transaction.domain.shared.Transaction;
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
    
    @PostMapping
    public ResponseEntity<Object> postEvent(@RequestBody EventIn eventIn) {
        
        log.debug("eventIn {}", eventIn);
        
        var event = eventIn.toEvent();
        
        var out = transaction.perform(cache, event);
        
        if (Constants.NON_EXISTING_ACCOUNT.equals(out.getMessage())) {
            return ResponseEntity.status(HttpStatus.NOT_FOUND).body("0");
        }
        
        var eventOut = new EventOut(out);
        
        var uri = ServletUriComponentsBuilder.fromCurrentRequest()
                .build()
                .toUri();
        log.debug("eventOut {}", eventOut);
        return ResponseEntity.created(uri).body(eventOut);
    }
}
