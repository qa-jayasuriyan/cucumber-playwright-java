package com.utils;

import com.microsoft.playwright.*;

public class PlaywrightFactory {

    private static Playwright playwright;
    private static Browser browser;
    private static Page page;

    public static void initBrowser(boolean headless) {
        playwright = Playwright.create();

        browser = playwright.chromium().launch(new BrowserType.LaunchOptions()
                .setHeadless(headless)
                .setSlowMo(1000)); //  Slow down actions by 1 second each

        BrowserContext context = browser.newContext();
        page = context.newPage();
    }

    public static Page getPage() {
        if (page == null) {
            throw new IllegalStateException("Page is null. Call PlaywrightFactory.initBrowser() first.");
        }
        return page;
    }

    public static void closeBrowser() {
        if (browser != null) {
            page.waitForTimeout(2000); // small delay before closing
            browser.close();
            playwright.close();
        }
    }
}
