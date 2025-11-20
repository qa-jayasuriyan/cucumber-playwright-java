package com.test;

import com.microsoft.playwright.Page;
import com.pages.LoginPage;
import com.pages.ReceivePaymentsPage;
import com.utils.PlaywrightFactory;
import io.cucumber.java.en.*;

public class ReceivePaymentsSteps {

    private Page page;
    private LoginPage loginPage;
    private ReceivePaymentsPage receivePaymentsPage;

    @Given("User is logged into the dashboard for receive payments")
    public void user_is_logged_into_the_dashboard_for_receive_payments() {
        page = PlaywrightFactory.getPage();
        loginPage = new LoginPage();
        loginPage.navigateToLogin();
        loginPage.loginWithStaticOTP(); // uses your existing login flow
    }

    @When("User creates a new receive payment with invoice file")
    public void user_creates_a_new_receive_payment_with_invoice_file() {
        receivePaymentsPage = new ReceivePaymentsPage();
        receivePaymentsPage.createReceivePayment(); // we’ll define this next
    }

    @Then("Receive payment should be created successfully")
    public void receive_payment_should_be_created_successfully() {
        receivePaymentsPage.verifyReceivePayment();
    }
}
