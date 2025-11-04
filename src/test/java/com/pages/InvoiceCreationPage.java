package com.pages;

import com.microsoft.playwright.*;
import com.microsoft.playwright.options.AriaRole;
import com.utils.InvoiceNumberManager;
import com.utils.PlaywrightFactory;

public class InvoiceCreationPage {

    private final Page page;

    // ✅ Same pattern as your other working pages
    public InvoiceCreationPage() {
        this.page = PlaywrightFactory.getPage();
    }

    public void createInvoice() {
        page.waitForTimeout(2000);
        System.out.println("🧾 Starting Invoice Creation Flow...");

        // --- same flow as before (form open, select client, currency, items, submit) ---
        page.getByRole(AriaRole.BUTTON, new Page.GetByRoleOptions().setName("form Create Invoice")).click();

        page.getByText("- Select Client -").click();
        page.getByText("jai", new Page.GetByTextOptions().setExact(true)).click();

        page.getByRole(AriaRole.COMBOBOX, new Page.GetByRoleOptions().setName("Transaction Type *")).click();
        page.getByText("Goods", new Page.GetByTextOptions().setExact(true)).click();

        String invoiceNumber = InvoiceNumberManager.getNextInvoiceNumber();
        page.getByRole(AriaRole.TEXTBOX, new Page.GetByRoleOptions().setName("Invoice ID *"))
            .fill(invoiceNumber);
        System.out.println("🧾 Invoice Number: " + invoiceNumber);

        page.locator(".ant-select-selector").nth(1).click();
        page.waitForSelector(".ant-select-item-option-content:has-text('USD')");
        page.locator(".ant-select-item-option-content:has-text('USD')").click();

        page.getByRole(AriaRole.TEXTBOX, new Page.GetByRoleOptions().setName("Invoice Date *")).click();
        page.getByTitle("-11-01").click();

        page.getByRole(AriaRole.COMBOBOX,
                new Page.GetByRoleOptions().setName("Purpose Code question-circle *")).click();
        page.getByText("P0103 - Advance receipts").click();

        page.getByRole(AriaRole.TEXTBOX, new Page.GetByRoleOptions().setName("Due Date *")).click();
        page.getByRole(AriaRole.CELL, new Page.GetByRoleOptions().setName("10")).locator("div").click();

        page.getByRole(AriaRole.BUTTON, new Page.GetByRoleOptions().setName("Add Items")).click();
        page.getByRole(AriaRole.TEXTBOX, new Page.GetByRoleOptions().setName("Item Description *"))
            .fill("software license");

        page.getByRole(AriaRole.SPINBUTTON, new Page.GetByRoleOptions().setName("Quantity *").setExact(true)).fill("1");
        page.getByRole(AriaRole.SPINBUTTON, new Page.GetByRoleOptions().setName("Rate Per Quantity *")).fill("100");

        page.getByRole(AriaRole.BUTTON, new Page.GetByRoleOptions().setName("Save & Close")).click();

        page.getByRole(AriaRole.TEXTBOX, new Page.GetByRoleOptions().setName("Payment Terms *"))
            .fill("Net 10 Days");

        page.getByRole(AriaRole.BUTTON, new Page.GetByRoleOptions().setName("Submit")).click();
        System.out.println("🚀 Submitted Invoice Successfully.");
    }

    public void verifyInvoiceCreation() {
        Locator toast = page.locator("div[role='alert']");
        toast.waitFor(new Locator.WaitForOptions().setTimeout(30000));
        String message = toast.innerText();
        System.out.println("✅ Invoice Success Toast: " + message);

        page.locator("span").filter(new Locator.FilterOptions().setHasText("Dashboard")).first().click();
        System.out.println("🏠 Returned to Dashboard.");
    }
}
