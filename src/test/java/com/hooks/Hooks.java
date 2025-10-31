package com.hooks;

import com.microsoft.playwright.Page;
import io.qameta.allure.Allure;
import com.utils.PlaywrightFactory;
import io.cucumber.java.*;
import java.io.ByteArrayInputStream;

public class Hooks {

    @Before
    public void setUp() {
        System.out.println("🚀 [HOOK] Launching Playwright before scenario...");
        PlaywrightFactory.initBrowser(false);
    }

    @After
    public void tearDown(Scenario scenario) {
        Page page = PlaywrightFactory.getPage();

        if (scenario.isFailed()) {
            System.out.println("❌ Scenario failed: capturing screenshot...");
            byte[] screenshot = page.screenshot(new Page.ScreenshotOptions().setFullPage(true));
            Allure.addAttachment("Failure Screenshot", new ByteArrayInputStream(screenshot));
        }

        System.out.println("🧹 [HOOK] Closing Playwright after scenario...");
        PlaywrightFactory.closeBrowser();
    }
}
