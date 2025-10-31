package com.pages;

import com.microsoft.playwright.*;
import com.microsoft.playwright.options.AriaRole;
import com.utils.InvoiceNumberManager;
import com.utils.PlaywrightFactory;

public class CreateAndClaimReceivablesPage {

    private final Page page;

    public CreateAndClaimReceivablesPage() {
        this.page = PlaywrightFactory.getPage();
    }

    /**
     * Create and claim a receivable (invoice)
     */
    public String createAndClaimReceivable() {
        page.waitForTimeout(2000);
        System.out.println("🔹 Starting Receivable Creation...");

        // Click "Create Invoice"
        page.getByRole(AriaRole.BUTTON, new Page.GetByRoleOptions().setName("form Create Invoice")).click();

        // Select Client
        page.getByText("- Select Client -").click();
        page.getByText("jai", new Page.GetByTextOptions().setExact(true)).click();

        // Select Transaction Type
        page.getByRole(AriaRole.COMBOBOX, new Page.GetByRoleOptions().setName("Transaction Type *")).click();
        page.getByText("Services", new Page.GetByTextOptions().setExact(true)).click();

        // Generate Invoice Number
        String invoiceNumber = InvoiceNumberManager.getNextInvoiceNumber();
        page.getByRole(AriaRole.TEXTBOX, new Page.GetByRoleOptions().setName("Invoice ID *")).fill(invoiceNumber);
        System.out.println("🧾 Invoice Generated: " + invoiceNumber);

        // Select Currency
        page.getByRole(AriaRole.COMBOBOX, new Page.GetByRoleOptions().setName("Currency of the Invoice *")).click();
        page.getByTitle("USD").locator("div").click();

        // Select Invoice Date
        page.getByRole(AriaRole.TEXTBOX, new Page.GetByRoleOptions().setName("Invoice Date *")).click();
        page.getByTitle("-10-01").click();

        // Select Purpose Code
        page.getByRole(AriaRole.COMBOBOX, new Page.GetByRoleOptions().setName("Purpose Code question-circle *")).click();
        page.getByText("P0103 - Advance receipts").click();

        // Select Due Date
        page.getByRole(AriaRole.TEXTBOX, new Page.GetByRoleOptions().setName("Due Date *")).click();
        page.getByRole(AriaRole.CELL, new Page.GetByRoleOptions().setName("10")).locator("div").click();

        // Add Items
        page.getByRole(AriaRole.BUTTON, new Page.GetByRoleOptions().setName("Add Items")).click();
        page.getByRole(AriaRole.TEXTBOX, new Page.GetByRoleOptions().setName("Item Description *")).fill("Trade Claim Test");

        // ✅ FIXED: Unique locators for Quantity and Rate fields
        page.getByLabel("Quantity", new Page.GetByLabelOptions().setExact(true)).fill("1");
        page.getByLabel("Rate Per Quantity").fill("50");

        // Save item
        page.getByRole(AriaRole.BUTTON, new Page.GetByRoleOptions().setName("Save & Close")).click();

        // Fill Payment Terms
        page.getByRole(AriaRole.TEXTBOX, new Page.GetByRoleOptions().setName("Payment Terms *"))
            .fill("check create receivable and claim ");

        // Click "Create Receivable"
        page.getByRole(AriaRole.BUTTON, new Page.GetByRoleOptions().setName("Create Receivable")).click();

        // Wait and click popup "CREATE RECEIVABLE"
        Locator confirmBtn = page.locator("div.ant-modal-content button:has-text('CREATE RECEIVABLE')").first();
        confirmBtn.waitFor(new Locator.WaitForOptions().setTimeout(10000));
        confirmBtn.click();
        System.out.println("✅ Clicked CREATE RECEIVABLE successfully.");

        // Wait for success toast
        Locator toast = page.locator("div[role='alert']");
        toast.waitFor(new Locator.WaitForOptions().setTimeout(30000));
        System.out.println("✅ Receivable Created Toast: " + toast.innerText());

        // Now claim same invoice
        claimReceivable(invoiceNumber);

        return invoiceNumber;
    }

    /**
     * Claim the created receivable.
     */
    private void claimReceivable(String invoiceNumber) {
        System.out.println("🔹 Starting Claim for invoice: " + invoiceNumber);

        page.waitForTimeout(2000);
        page.locator("span:has-text('Dashboard')").first().click();
        page.waitForTimeout(2000);

        // Locate the row for the created invoice
        Locator row = page.getByRole(AriaRole.ROW)
                .filter(new Locator.FilterOptions().setHasText(invoiceNumber))
                .first();

        row.getByRole(AriaRole.BUTTON).click();
        page.waitForTimeout(1000);

        // Fill Claim Amount
        page.getByPlaceholder("Enter amount").fill("50");

        // Submit the claim
        page.getByLabel("Reconciliation - jai")
                .getByRole(AriaRole.BUTTON, new Locator.GetByRoleOptions().setName("Submit"))
                .click();

        System.out.println("✅ Claim submitted for invoice: " + invoiceNumber);

        // Verify Claim Success
        Locator toast = page.locator("div[role='alert']");
        toast.waitFor(new Locator.WaitForOptions().setTimeout(20000));
        System.out.println("🎉 Claim Success Toast: " + toast.innerText());
    }
}
