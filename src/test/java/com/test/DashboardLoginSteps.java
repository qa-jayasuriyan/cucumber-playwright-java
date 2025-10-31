package com.test;

import com.pages.LoginPage;
import io.cucumber.java.en.*;
import static org.junit.Assert.assertTrue;

public class DashboardLoginSteps {
    private LoginPage loginPage;

    @Given("User is on login page")
    public void user_is_on_login_page() {
        loginPage = new LoginPage();
        loginPage.navigateToLogin();
    }

    @When("User enters email and submits OTP")
    public void user_enters_email_and_submits_otp() {
        loginPage.loginWithStaticOTP();
    }

    @Then("User should land on dashboard")
    public void user_should_land_on_dashboard() {
        assertTrue("Dashboard not visible after login!", loginPage.verifyDashboard());
    }
}
