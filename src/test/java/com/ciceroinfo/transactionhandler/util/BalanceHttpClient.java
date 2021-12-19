package com.ciceroinfo.transactionhandler.util;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.web.client.RestTemplateBuilder;
import org.springframework.boot.web.server.LocalServerPort;
import org.springframework.context.annotation.Scope;
import org.springframework.http.ResponseEntity;
import org.springframework.stereotype.Component;

import static io.cucumber.spring.CucumberTestContext.SCOPE_CUCUMBER_GLUE;

@Component
@Scope(SCOPE_CUCUMBER_GLUE)
public class BalanceHttpClient implements HttpClient {
    
    @LocalServerPort
    private int port;
    
    @Autowired
    private RestTemplateBuilder restTemplateBuilder;
    
    @Override
    public String endpoint() {
        return SERVER_URL + ":" + port + "/balance?account_id={account_id}";
    }
    
    public ResponseEntity<String> balance(Long accountId) {
        var restTemplate = restTemplateBuilder.errorHandler(new RestTemplateResponseErrorHandler()).build();
        return restTemplate.getForEntity(endpoint(), String.class, accountId);
    }
}