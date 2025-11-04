package com.test;

import org.junit.runner.RunWith;
import io.cucumber.junit.Cucumber;
import io.cucumber.junit.CucumberOptions;

@RunWith(Cucumber.class)
@CucumberOptions(
    features = {
        "src/test/resources/features/DashboardLogin.feature",
        "src/test/resources/features/InvoiceCreation.feature",
        "src/test/resources/features/CreateReceivable.feature",
        "src/test/resources/features/CreateAndClaimReceivable.feature"
    },
    glue = {"com.test", "com.pages", "com.hooks"},
    plugin = {
        "pretty",
        "html:target/cucumber-reports/cucumber.html",
        "json:target/cucumber-reports/cucumber.json",
        "io.qameta.allure.cucumber7jvm.AllureCucumber7Jvm"
    },
    monochrome = true
)
public class TestRunner {}