package com.ciceroinfo.transactionhandler.bdd.steps;

import com.ciceroinfo.transactionhandler.util.BalanceHttpClient;
import com.ciceroinfo.transactionhandler.util.ResetHttpClient;
import io.cucumber.java.en.And;
import io.cucumber.java.en.Then;
import io.cucumber.java.en.When;
import org.junit.Assert;
import org.springframework.beans.factory.annotation.Autowired;

public class BalanceSteps extends CommonSteps {
    
    @Autowired
    BalanceHttpClient client;
    
    @When("the client calls balance")
    public void theClientCallsBalance() {
    }
    
    @When("the client calls balance for a client {long}")
    public void theClientCallsBalanceForAClient(Long clientId) {
        var response = client.balance(clientId);
        responseHttpStatus = response.getStatusCode();
        responseBody = response.getBody();
    }
    
    @Then("the client receives a balance status code of {int}")
    public void theClientReceivesABalanceStatusCodeOf(int statusCode) {
        Assert.assertEquals("Invalid HTTP status code", statusCode, responseHttpStatus.value());
    }
    
    
    @And("the client receives a balance response body as 'ZERO'")
    public void theClientReceivesABalanceResponseBodyAsZERO() {
        Assert.assertEquals("Not a expected body response", ZERO, responseBody);
    }
}
