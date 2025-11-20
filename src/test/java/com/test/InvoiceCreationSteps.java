package com.test;

import com.pages.InvoiceCreationPage;
import com.pages.LoginPage;
import io.cucumber.java.en.*;

public class InvoiceCreationSteps {

    private LoginPage loginPage;
    private InvoiceCreationPage invoicePage;

    @Given("User is logged into the dashboard for invoice creation")
    public void user_is_logged_into_the_dashboard_for_invoice_creation() {
        System.out.println(" Logging into KarbonFX dashboard...");
        loginPage = new LoginPage();
        loginPage.navigateToLogin();
        loginPage.loginWithStaticOTP(); 
        System.out.println(" Login successful, dashboard loaded.");
    }

    @When("User creates a new invoice")
    public void user_creates_a_new_invoice() {
        System.out.println(" Starting new invoice creation...");
        invoicePage = new InvoiceCreationPage();  //  self-initialized with PlaywrightFactory
        invoicePage.createInvoice();
        System.out.println(" Invoice creation flow completed.");
    }

    @Then("Invoice should be created successfully")
    public void invoice_should_be_created_successfully() {
        System.out.println(" Verifying invoice creation...");
        invoicePage.verifyInvoiceCreation();
        System.out.println(" Invoice verified successfully!");
    }
}
