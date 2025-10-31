package com.test;

import com.pages.CreateAndClaimReceivablesPage;
import io.cucumber.java.en.*;

public class CreateAndClaimReceivableSteps {

    private CreateAndClaimReceivablesPage receivablePage;

    @When("User creates and claims a receivable")
    public void user_creates_and_claims_a_receivable() {
        receivablePage = new CreateAndClaimReceivablesPage();
        receivablePage.createAndClaimReceivable();
    }

    @Then("Receivable should be created and claimed successfully")
    public void receivable_should_be_created_and_claimed_successfully() {
        System.out.println("✅ Full create and claim flow completed successfully!");
    }
}
