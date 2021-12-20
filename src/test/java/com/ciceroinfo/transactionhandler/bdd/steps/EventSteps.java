package com.ciceroinfo.transactionhandler.bdd.steps;

import com.ciceroinfo.transactionhandler.transaction.application.EventInput;
import com.ciceroinfo.transactionhandler.util.EventHttpClient;
import com.fasterxml.jackson.core.JsonProcessingException;
import com.fasterxml.jackson.databind.ObjectMapper;
import io.cucumber.java.en.And;
import io.cucumber.java.en.Given;
import io.cucumber.java.en.Then;
import io.cucumber.java.en.When;
import org.junit.Assert;
import org.springframework.beans.factory.annotation.Autowired;

import java.math.BigDecimal;

public class EventSteps extends CommonSteps {
    
    @Autowired
    EventHttpClient client;
    
    EventInput input;
    
    @Given("a new event {string} and {string} with {long}")
    public void aNewEventAndWith(String type, String destination, long amount) {
        input = EventInput.builder().type(type).destination(destination).amount(BigDecimal.valueOf(amount)).build();
    }
    
    @Given("a new event {string} type for destination {string} with an amount of {long}")
    public void aNewEventTypeForDestinationWithAnAmountOf(String type, String destination, Long amount) {
        
        Assert.assertNotNull(type);
        Assert.assertFalse(type.isEmpty());
        Assert.assertNotNull(destination);
        Assert.assertFalse(destination.isEmpty());
        Assert.assertNotNull(amount);
        Assert.assertFalse("Amount must be greater than zero", amount < 1);
        
        input = EventInput.builder().type(type).destination(destination).amount(BigDecimal.valueOf(amount)).build();
    }
    
    @When("the client calls event request")
    public void theClientCallsEventRequest() {
        
        var response = client.event(input);
        
        Assert.assertNotNull(response);
        
        responseHttpStatus = response.getStatusCode();
        responseBody = response.getBody();
    }
    
    @Then("the client receives a HTTP event status code of {int}")
    public void theClientReceivesAHTTPEventStatusCodeOf(int statusCode) {
        Assert.assertEquals("Invalid HTTP status code", statusCode, responseHttpStatus.value());
    }
    
    @And("the follow json values response, destination id {string} and balance of {long}")
    public void theFollowJsonValuesResponseDestinationIdAndBalanceOf(String destination, long balance) throws JsonProcessingException {
        
        ObjectMapper mapper = new ObjectMapper();
        
        var output = mapper.readTree(responseBody);
        
        Assert.assertNull(output.get("origin"));
        Assert.assertNotNull(output.get("destination"));
        Assert.assertEquals(destination, output.get("destination").get("id").textValue());
        Assert.assertEquals(balance, output.get("destination").get("balance").longValue());
    }
}
