package com.testng;

import org.openqa.selenium.WebDriver;
import org.openqa.selenium.remote.RemoteWebDriver;
import org.openqa.selenium.remote.DesiredCapabilities;
import org.testng.annotations.AfterMethod;
import org.testng.annotations.BeforeMethod;
import org.testng.annotations.Test;
import java.net.URL; // Add this import statement

public class DynamicElementUsingXpath {

    WebDriver driver;

    @BeforeMethod
    public void setUp() throws Exception {
        DesiredCapabilities capabilities = new DesiredCapabilities();
        capabilities.setBrowserName("edge");

        driver = new RemoteWebDriver(new URL("http://localhost:4444"), capabilities);
        driver.get("https://www.google.com");
    }

    @Test
    public void testGoogleSearch() {
        System.out.println("Page Title: " + driver.getTitle());
        assert driver.getTitle().contains("Google");
    }

    @AfterMethod
    public void tearDown() {
        if (driver != null) {
            driver.quit();
        }
    }
}