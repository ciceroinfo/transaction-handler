package com.ciceroinfo.transactionhandler.util;

import org.springframework.beans.factory.annotation.Value;
import org.springframework.boot.web.server.LocalServerPort;
import org.springframework.context.annotation.Scope;
import org.springframework.http.ResponseEntity;
import org.springframework.stereotype.Component;
import org.springframework.web.client.RestTemplate;

import static io.cucumber.spring.CucumberTestContext.SCOPE_CUCUMBER_GLUE;

@Component
@Scope(SCOPE_CUCUMBER_GLUE)
public class ResetHttpClient implements HttpClient {
    
    @LocalServerPort
    private int port;
    
    @Value("${server.servlet.context-path}")
    private String context;
    
    private final RestTemplate restTemplate = new RestTemplate();
    
    @Override
    public String endpoint() {
        return SERVER_URL + ":" + port + context + "/reset";
    }
    
    public ResponseEntity<String> reset() {
        return restTemplate.postForEntity(endpoint(), null, String.class);
    }
}