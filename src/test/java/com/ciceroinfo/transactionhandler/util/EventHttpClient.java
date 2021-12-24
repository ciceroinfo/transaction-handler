package com.ciceroinfo.transactionhandler.util;

import com.ciceroinfo.transactionhandler.transaction.application.event.EventIn;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.boot.web.client.RestTemplateBuilder;
import org.springframework.boot.web.server.LocalServerPort;
import org.springframework.context.annotation.Scope;
import org.springframework.http.ResponseEntity;
import org.springframework.stereotype.Component;

import static io.cucumber.spring.CucumberTestContext.SCOPE_CUCUMBER_GLUE;

@Component
@Scope(SCOPE_CUCUMBER_GLUE)
public class EventHttpClient implements HttpClient {
    
    @LocalServerPort
    private int port;
    
    @Value("${server.servlet.context-path}")
    private String context;
    
    @Autowired
    private RestTemplateBuilder restTemplateBuilder;
    
    @Override
    public String endpoint() {
        return SERVER_URL + ":" + port + context + "/event";
    }
    
    public ResponseEntity<String> event(EventIn input) {
        var restTemplate = restTemplateBuilder.errorHandler(new RestTemplateResponseErrorHandler()).build();
        return restTemplate.postForEntity(endpoint(), input, String.class);
    }
}