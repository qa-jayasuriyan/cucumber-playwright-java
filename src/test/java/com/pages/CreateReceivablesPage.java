package com.pages;

import com.microsoft.playwright.*;
import com.microsoft.playwright.options.AriaRole;
import com.utils.InvoiceNumberManager;
import com.utils.PlaywrightFactory;

public class CreateReceivablesPage {

    private final Page page;

    public CreateReceivablesPage() {
        this.page = PlaywrightFactory.getPage();
    }

    public void createReceivable() {
        page.waitForTimeout(2000);

        // Click main "Create Invoice" form button
        page.getByRole(AriaRole.BUTTON, new Page.GetByRoleOptions().setName("form Create Invoice")).click();

        // Fill details
        page.getByText("- Select Client -").click();
        page.getByText("jai", new Page.GetByTextOptions().setExact(true)).click();
        page.getByRole(AriaRole.COMBOBOX, new Page.GetByRoleOptions().setName("Transaction Type *")).click();
        page.getByText("Services", new Page.GetByTextOptions().setExact(true)).click();

        // Generate invoice number
        String invoiceNumber = InvoiceNumberManager.getNextInvoiceNumber();
        page.getByRole(AriaRole.TEXTBOX, new Page.GetByRoleOptions().setName("Invoice ID *")).fill(invoiceNumber);
        System.out.println("Invoice Generated: " + invoiceNumber);

        // Currency selection
        page.getByRole(AriaRole.COMBOBOX, new Page.GetByRoleOptions().setName("Currency of the Invoice *")).click();
        page.getByTitle("USD").locator("div").click();

        // Dates
        page.getByRole(AriaRole.TEXTBOX, new Page.GetByRoleOptions().setName("Invoice Date *")).click();
        page.getByTitle("-11-01").click();

        page.getByRole(AriaRole.COMBOBOX, new Page.GetByRoleOptions().setName("Purpose Code question-circle *")).click();
        page.getByText("P0103 - Advance receipts").click();

        page.getByRole(AriaRole.TEXTBOX, new Page.GetByRoleOptions().setName("Due Date *")).click();
        page.getByRole(AriaRole.CELL, new Page.GetByRoleOptions().setName("10")).locator("div").click();

        // Add items
        page.getByRole(AriaRole.BUTTON, new Page.GetByRoleOptions().setName("Add Items")).click();
        page.getByRole(AriaRole.TEXTBOX, new Page.GetByRoleOptions().setName("Item Description *")).fill("trade check");

        page.getByRole(AriaRole.SPINBUTTON, new Page.GetByRoleOptions().setName("Quantity *").setExact(true)).fill("1");
        page.getByRole(AriaRole.SPINBUTTON, new Page.GetByRoleOptions().setName("Rate Per Quantity *")).fill("50");

        page.getByRole(AriaRole.BUTTON, new Page.GetByRoleOptions().setName("Save & Close")).click();

        // Fill payment terms
        page.getByRole(AriaRole.TEXTBOX, new Page.GetByRoleOptions().setName("Payment Terms *"))
            .fill("check create receivables");

        // Click main "Create Receivable" button
        System.out.println("🟡 Clicked Create Receivable main button");
        page.getByRole(AriaRole.BUTTON, new Page.GetByRoleOptions().setName("Create Receivable").setExact(true)).click();

        // Wait for popup modal to appear
        page.waitForSelector("div.ant-modal-content", new Page.WaitForSelectorOptions().setTimeout(10000));
        page.waitForTimeout(1000);

        // Click correct popup confirmation button (avoids strict mode issue)
        Locator popupButton = page.locator("button.ant-btn-default.custom_button.primary_")
                .filter(new Locator.FilterOptions().setHasText("CREATE RECEIVABLE"))
                .first();

        popupButton.waitFor(new Locator.WaitForOptions().setTimeout(8000));
        popupButton.click();
        System.out.println("✅ Clicked CREATE RECEIVABLE in popup successfully.");

        // Small wait for backend processing
        page.waitForTimeout(3000);
    }

    public boolean verifyReceivable() {
        System.out.println("🧾 Waiting for success toast...");
        Locator toast = page.locator("div[role='alert']");
        toast.waitFor(new Locator.WaitForOptions().setTimeout(30000));
        String message = toast.innerText();
        System.out.println("✅ Success toast visible: " + message);
        page.waitForTimeout(1000);
        return true;
    }
}
