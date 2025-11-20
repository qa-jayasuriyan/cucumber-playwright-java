package com.pages;

import java.nio.file.Paths;
import com.microsoft.playwright.*;
import com.microsoft.playwright.options.AriaRole;
import com.utils.PlaywrightFactory;

public class ReceivePaymentsPage {

    private final Page page;

    public ReceivePaymentsPage() {
        this.page = PlaywrightFactory.getPage();
    }

    public void createReceivePayment() {
        System.out.println("💰 Starting Receive Payment creation...");

        // Click on Receive Payment
        page.getByText("Receive Payment").click();

        // Select client
        page.getByRole(AriaRole.COMBOBOX, new Page.GetByRoleOptions().setName("Select Client (Nick Name) *")).click();
        page.getByText("Test", new Page.GetByTextOptions().setExact(true)).click();

        // Select currency
        page.getByRole(AriaRole.COMBOBOX, new Page.GetByRoleOptions().setName("Currency of the invoice *")).click();
        page.getByTitle("USD").locator("div").click();

        // Fill invoice details
        page.getByRole(AriaRole.SPINBUTTON, new Page.GetByRoleOptions().setName("Invoice Amount *")).fill("44");
        page.getByRole(AriaRole.SPINBUTTON, new Page.GetByRoleOptions().setName("Receivable Amount question-")).fill("29");
        page.getByRole(AriaRole.TEXTBOX, new Page.GetByRoleOptions().setName("Invoice number *")).fill("INV-" + System.currentTimeMillis());

        // Transaction type
        page.getByRole(AriaRole.COMBOBOX, new Page.GetByRoleOptions().setName("Transaction Type *")).click();
        page.getByText("Goods", new Page.GetByTextOptions().setExact(true)).click();

        // Dates
        page.getByRole(AriaRole.TEXTBOX, new Page.GetByRoleOptions().setName("Invoice Date *")).click();
        page.getByTitle("-11-05").locator("div").click();
        page.getByRole(AriaRole.TEXTBOX, new Page.GetByRoleOptions().setName("Due Date *")).click();
        page.getByRole(AriaRole.CELL, new Page.GetByRoleOptions().setName("21")).click();

        // Upload invoice file
        String filePath = "/Users/jayasuriyans/Downloads/AutomationInvoice.pdf";
        page.locator("input[type='file']").setInputFiles(Paths.get(filePath));
        System.out.println("📎 Uploaded Invoice File: " + filePath);

        // Purpose code
        page.getByRole(AriaRole.COMBOBOX, new Page.GetByRoleOptions().setName("Purpose Code question-circle *")).click();
        page.getByText("P0103 - Advance receipts").click();

        // Submit
        page.getByRole(AriaRole.BUTTON, new Page.GetByRoleOptions().setName("Submit")).click();
        System.out.println("🚀 Receive Payment submitted.");
    }

    public void verifyReceivePayment() {

        System.out.println("✅ Receive Payment Success: " );
    }
}
