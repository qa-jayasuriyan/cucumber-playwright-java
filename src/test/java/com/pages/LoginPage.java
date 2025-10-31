package com.pages;

import com.microsoft.playwright.Page;
import com.microsoft.playwright.options.AriaRole;
import com.utils.PlaywrightFactory;

import static com.microsoft.playwright.assertions.PlaywrightAssertions.assertThat;

import com.microsoft.playwright.Locator;

public class LoginPage {

    private Page page;

    public LoginPage() {
        this.page = PlaywrightFactory.getPage();
    }

    public void navigateToLogin() {
        page.navigate("https://app.dev.karbonfx.com/login/"); // ✅ Use your actual app URL
    }

    public void loginWithStaticOTP() {
        page.getByRole(AriaRole.TEXTBOX, new Page.GetByRoleOptions().setName("Enter your email address *"))
                .fill("narasingam.m+128@karboncard.com");

        page.getByRole(AriaRole.BUTTON, new Page.GetByRoleOptions().setName("Send OTP")).click();

        // Fill static OTP automatically (000000)
        page.locator(".ant-input").nth(0).fill("0");
        page.locator("input:nth-child(2)").fill("0");
        page.locator("input:nth-child(3)").fill("0");
        page.locator("input:nth-child(4)").fill("0");
        page.locator("input:nth-child(5)").fill("0");
        page.locator("input:nth-child(6)").fill("0");

        page.getByRole(AriaRole.BUTTON, new Page.GetByRoleOptions().setName("Verify OTP")).click();
        page.waitForTimeout(2000); // 🕐 Wait 2 seconds for dashboard to load

    }
    public boolean verifyDashboard() {
        // Wait up to 15 seconds for any "Dashboard" text to appear
        page.waitForSelector("text=Dashboard", new Page.WaitForSelectorOptions().setTimeout(15000));

        // Get all "Dashboard" elements and check which one is visible
        Locator dashboards = page.locator("text=Dashboard");

        //  Check if any of them is visible and print debug info
        int count = dashboards.count();
        System.out.println("Found " + count + " Dashboard elements.");

        for (int i = 0; i < count; i++) {
            Locator item = dashboards.nth(i);
            if (item.isVisible()) {
                System.out.println(" Dashboard visible at index " + i);
                return true;
            }
        }

        System.out.println("❌ No visible Dashboard element found!");
        return false;
    }

}
