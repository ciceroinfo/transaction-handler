package com.ciceroinfo.transactionhandler.transaction.application;

import lombok.extern.slf4j.Slf4j;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;
import org.springframework.web.servlet.support.ServletUriComponentsBuilder;

@Slf4j
@RestController
@RequestMapping("/event")
public class EventController {
    
    @PostMapping
    public ResponseEntity<EventOutput> postEvent(@RequestBody EventInput eventInput) {
        log.info("accountId {}", eventInput);
        
        Destination destination = null;
        
        if (eventInput.getType().equals("deposit")) {
            destination = Destination.builder().id(eventInput.getDestination()).balance(eventInput.getAmount()).build();
        }
        
        var eventOutput = EventOutput.builder().destination(destination).build();
        
        var uri = ServletUriComponentsBuilder.fromCurrentRequest()
                .path("/{id}")
                .buildAndExpand(eventInput.getDestination())
                .toUri();
        return ResponseEntity.created(uri).body(eventOutput);
    }
}
