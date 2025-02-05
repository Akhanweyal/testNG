package com.testng;
import java.time.Duration;
import org.openqa.selenium.WebDriver;
import org.openqa.selenium.WebElement;
import org.openqa.selenium.edge.EdgeDriver;
import org.openqa.selenium.support.ui.ExpectedConditions;
import org.openqa.selenium.support.ui.WebDriverWait;
import org.testng.annotations.AfterMethod;
import org.testng.annotations.BeforeMethod;
import org.testng.annotations.Test;
import io.github.bonigarcia.wdm.WebDriverManager;
import org.openqa.selenium.By;
public class DynamicElementUisngExplicitWait {
    WebDriver driver;

    @BeforeMethod
    public void setUp() {
        // Set up EdgeDriver using WebDriverManager
        WebDriverManager.edgedriver().setup();
        driver = new EdgeDriver();
         driver.get("https://www.google.com");

    }

    @Test
    public void WaitforElementExplicitly() {
        // Wait for the search input field to be visible
        WebDriverWait wait = new WebDriverWait(driver, Duration.ofSeconds(10));
        WebElement searchInput = wait.until(ExpectedConditions.visibilityOfElementLocated(By.name("q")));

        // Print the placeholder text of the search input field
        System.out.println("Search Input Placeholder: " + searchInput.getAttribute("placeholder"));
    }
    @Test
   public void validateYouLandedOnThePage(){
    String title = driver.getTitle();
    assert title.contains("Google");
  }     
   
   @Test
   public void ValidateSearchField (){
    WebElement searchFieled =driver.findElement(By.id("APjFqb"));
    searchFieled.click();
    searchFieled.sendKeys("ajmal");
   }

    @AfterMethod
    public void tearDown() {
        if (driver != null) {
            driver.quit();
        }
    }
}