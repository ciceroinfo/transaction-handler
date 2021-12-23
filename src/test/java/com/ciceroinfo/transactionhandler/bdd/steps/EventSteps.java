package com.ciceroinfo.transactionhandler.bdd.steps;

import com.ciceroinfo.transactionhandler.transaction.application.event.EventIn;
import com.ciceroinfo.transactionhandler.util.EventHttpClient;
import com.fasterxml.jackson.core.JsonProcessingException;
import com.fasterxml.jackson.databind.ObjectMapper;
import io.cucumber.java.Before;
import io.cucumber.java.en.And;
import io.cucumber.java.en.Given;
import io.cucumber.java.en.Then;
import io.cucumber.java.en.When;
import org.junit.Assert;
import org.springframework.beans.factory.annotation.Autowired;

public class EventSteps extends CommonSteps {
    
    @Autowired
    EventHttpClient client;
    
    ObjectMapper mapper;
    
    EventIn input;
    
    @Before
    public void before()  {
        mapper = new ObjectMapper();
    }
    
    @Given("a new event {string} and {string} with {int}")
    public void aNewEventAndWith(String type, String destination, Integer amount) {
        input = EventIn.builder().type(type).destination(destination).amount(amount).build();
    }
    
    @Given("a new event {string} type for destination {string} with an amount of {int}")
    public void aNewEventTypeForDestinationWithAnAmountOf(String type, String destination, Integer amount) {
        
        Assert.assertNotNull(type);
        Assert.assertFalse(type.isEmpty());
        Assert.assertNotNull(destination);
        Assert.assertFalse(destination.isEmpty());
        Assert.assertNotNull(amount);
        Assert.assertFalse("Amount must be greater than zero", amount < 1);
        
        input = EventIn.builder().type(type).destination(destination).amount(amount).build();
    }
    
    @Given("a new event {string} type from origin account {string} with an amount of {int}")
    public void aNewEventTypeForOriginWithAnAmountOf(String type, String origin, Integer amount) {
        Assert.assertNotNull(type);
        Assert.assertFalse(type.isEmpty());
        Assert.assertNotNull(origin);
        Assert.assertFalse(origin.isEmpty());
        Assert.assertNotNull(amount);
        Assert.assertFalse("Amount must be greater than zero", amount < 1);
    
        input = EventIn.builder().type(type).origin(origin).amount(amount).build();
    }
    
    @Given("a new event {string} type from origin account {string} with an amount of {int} to destination {string}")
    public void aNewEventTypeFromOriginAccountWithAnAmountOfToDestination(String type, String origin, Integer amount,
                                                                          String destination) {
        Assert.assertNotNull(type);
        Assert.assertFalse(type.isEmpty());
        Assert.assertNotNull(origin);
        Assert.assertFalse(origin.isEmpty());
        Assert.assertNotNull(amount);
        Assert.assertFalse("Amount must be greater than zero", amount < 1);
    
        input = EventIn.builder().type(type).origin(origin).amount(amount).destination(destination).build();
    }
    
    @When("the client calls event request")
    public void theClientCallsEventRequest() {
    
        Assert.assertNotNull("Event input cannot be null", input);
        
        var response = client.event(input);
        
        Assert.assertNotNull(response);
        
        responseHttpStatus = response.getStatusCode();
        responseBody = response.getBody();
    }
    
    @Then("the client receives a HTTP event status code of {int}")
    public void theClientReceivesAHTTPEventStatusCodeOf(int statusCode) {
        Assert.assertEquals("Invalid HTTP status code", statusCode, responseHttpStatus.value());
    }
    
    @And("the follow json values response, {string} id {string} and balance of {long}")
    public void theFollowJsonValuesResponseDestinationIdAndBalanceOf(String responseType, String destination,
                                                                     long balance) throws JsonProcessingException {
        
        mapper = new ObjectMapper();
        
        var output = mapper.readTree(responseBody);
        var responseTypeJsonNode = output.get(responseType);
        Assert.assertNotNull(responseTypeJsonNode);
        Assert.assertEquals(destination, responseTypeJsonNode.get("id").textValue());
        Assert.assertEquals(balance, responseTypeJsonNode.get("balance").longValue());
    }
    
    @And("lack of {string} value")
    public void lackOfValue(String responseType) throws JsonProcessingException {
        var output = mapper.readTree(responseBody);
        Assert.assertNull(output.get(responseType));
    }
}
