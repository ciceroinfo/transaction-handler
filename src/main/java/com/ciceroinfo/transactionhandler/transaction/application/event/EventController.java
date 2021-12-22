package com.ciceroinfo.transactionhandler.transaction.application.event;

import com.ciceroinfo.transactionhandler.transaction.application.shared.Constants;
import com.ciceroinfo.transactionhandler.transaction.domain.shared.AccountRepository;
import com.ciceroinfo.transactionhandler.transaction.domain.event.Deposit;
import com.ciceroinfo.transactionhandler.transaction.domain.event.End;
import com.ciceroinfo.transactionhandler.transaction.domain.event.Withdraw;
import com.ciceroinfo.transactionhandler.transaction.domain.shared.Transaction;
import com.ciceroinfo.transactionhandler.transaction.domain.shared.TransactionTypes;
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
import java.math.BigDecimal;

@Slf4j
@RestController
@RequestMapping("/event")
public class EventController {
    
    @Autowired
    private AccountRepository cache;
    
    private Transaction transaction;
    
    @PostConstruct
    public void init() {
        transaction = new Deposit(new Withdraw(new End()));
    }
    
    @PostMapping
    public ResponseEntity<Object> postEvent(@RequestBody EventInput eventInput) {
        
        log.info("eventInput {}", eventInput);
        
        var result = transaction.calc(cache, eventInput);
        
        if (Constants.NON_EXISTING_ACCOUNT.equals(result)) {
            return ResponseEntity.status(HttpStatus.NOT_FOUND).body("0");
        }
        
        Destination destination = null;
        Origin origin = null;
        
        if (TransactionTypes.DEPOSIT.equals(eventInput.getType())) {
            destination =
                    Destination.builder().id(eventInput.getDestination()).balance(new BigDecimal(cache.value(eventInput.getDestination()))).build();
        } else if (TransactionTypes.WITHDRAW.equals(eventInput.getType())) {
            
            var balance = cache.value(eventInput.getOrigin());
            origin = Origin.builder().id(eventInput.getOrigin()).balance(new BigDecimal(balance)).build();
        }
        
        var eventOutput = EventOutput.builder().origin(origin).destination(destination).build();
        
        var uri = ServletUriComponentsBuilder.fromCurrentRequest()
//                .path("/{id}")
//                .buildAndExpand(eventInput.getDestination())
                .build()
                .toUri();
        log.info("eventOutput {}", eventOutput);
        return ResponseEntity.created(uri).body(eventOutput);
    }
}
