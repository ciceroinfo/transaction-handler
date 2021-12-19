package com.ciceroinfo.transactionhandler.bdd;

import io.cucumber.junit.Cucumber;
import io.cucumber.junit.CucumberOptions;
import org.junit.runner.RunWith;

@RunWith(Cucumber.class)
@CucumberOptions(
        tags = "@reset",
        features = {"src/test/resources/features"},
        plugin = {"pretty", "json:target/cucumber.json"},
        glue = "com.ciceroinfo",
        monochrome = true
)
public class CucumberTest {
}