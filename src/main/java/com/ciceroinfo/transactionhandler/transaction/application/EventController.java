package com.ciceroinfo.transactionhandler.transaction.application;

import lombok.extern.slf4j.Slf4j;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;
import org.springframework.web.servlet.support.ServletUriComponentsBuilder;

import java.math.BigDecimal;
import java.text.DecimalFormat;

@Slf4j
@RestController
@RequestMapping("/event")
public class EventController {
    
    public static final String DEPOSIT = "deposit";
    
    @Autowired
    LocalCache cache;
    
    @PostMapping
    public ResponseEntity<EventOutput> postEvent(@RequestBody EventInput eventInput) {
        
        log.info("accountId {}", eventInput);
        
        var balance = cache.value(eventInput.getDestination());
        
        if (DEPOSIT.equals(eventInput.getType())) {
            
            if(balance == null) {
                cache.add(eventInput.getDestination(), eventInput.getAmount().toString());
            } else {
                
                cache.add(eventInput.getDestination(),
                        eventInput.getAmount().add(new BigDecimal(balance)).toString());
            }
        }
        
        
        System.out.println(">>>>>> " + cache.value(eventInput.getDestination()));
        
        
        Destination destination = null;
        
        if (eventInput.getType().equals("deposit")) {
            destination =
                    Destination.builder().id(eventInput.getDestination()).balance(new BigDecimal(cache.value(eventInput.getDestination()))).build();
        }
        
        var eventOutput = EventOutput.builder().destination(destination).build();
        
        var uri = ServletUriComponentsBuilder.fromCurrentRequest()
                .path("/{id}")
                .buildAndExpand(eventInput.getDestination())
                .toUri();
        
        return ResponseEntity.created(uri).body(eventOutput);
    }
}
