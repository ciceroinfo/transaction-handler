package com.ciceroinfo.transactionhandler.bdd.steps;

import com.ciceroinfo.transactionhandler.util.ResetEndpointHttpClient;
import io.cucumber.java.en.And;
import io.cucumber.java.en.Then;
import io.cucumber.java.en.When;
import org.junit.Assert;
import org.springframework.beans.factory.annotation.Autowired;

public class ResetSteps extends CommonSteps {
    
    @Autowired
    ResetEndpointHttpClient client;
    
    @When("the client calls reset system")
    public void theClientCallsResetSystem() {
        var response = client.reset();
        responseHttpStatus = response.getStatusCode();
        responseBody = response.getBody();
    }
    
    @Then("the client receives status code of {int}")
    public void theClientReceivesStatusCodeOf(int statusCode) {
        Assert.assertEquals("Invalid HTTPp status code", statusCode, responseHttpStatus.value());
    }
    
    @And("the client receives response body as {string}")
    public void theClientReceivesResponseBodyAsOK(String body) {
        Assert.assertEquals("Not a expected body response", body, responseBody);
    }
}
