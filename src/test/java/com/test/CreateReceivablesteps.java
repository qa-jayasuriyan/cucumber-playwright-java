package com.test;

import com.pages.CreateReceivablesPage;
import com.pages.LoginPage;
import io.cucumber.java.en.*;
import static org.junit.Assert.assertTrue;

public class CreateReceivablesteps {
    private LoginPage loginPage;
    private CreateReceivablesPage receivablePage;

    @Given("User is logged into the dashboard")
    public void user_is_logged_into_the_dashboard() {
        loginPage = new LoginPage();
        loginPage.navigateToLogin();
        loginPage.loginWithStaticOTP();
        assertTrue("Dashboard not visible!", loginPage.verifyDashboard());
    }

    @When("User creates a new receivable")
    public void user_creates_a_new_receivable() {
        receivablePage = new CreateReceivablesPage();
        receivablePage.createReceivable();
        System.out.println("Invoice number created successfully");
    }

    @Then("Receivable should be created successfully")
    public void receivable_should_be_created_successfully() {
        assertTrue("Receivable not created!", receivablePage.verifyReceivable());
    }
}
