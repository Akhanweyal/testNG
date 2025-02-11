package com.testng;
import java.time.Duration;
import org.openqa.selenium.WebDriver;
import org.openqa.selenium.WebElement;
import org.openqa.selenium.chrome.ChromeDriver;
import org.openqa.selenium.edge.EdgeDriver;
import org.openqa.selenium.support.ui.ExpectedConditions;
import org.openqa.selenium.support.ui.WebDriverWait;
import org.testng.Assert;
import org.testng.annotations.AfterMethod;
import org.testng.annotations.BeforeMethod;
import org.testng.annotations.Test;
import io.github.bonigarcia.wdm.WebDriverManager;
import org.openqa.selenium.By;
import org.openqa.selenium.JavascriptExecutor;

import java.util.List;

public class DynamicElementUisngExplicitWait {
    WebDriver driver;

    @BeforeMethod
    public void setUp() {
        // Set up EdgeDriver using WebDriverManager
        WebDriverManager.edgedriver().setup();
        driver = new EdgeDriver();
         driver.get("https://www.google.com");
        driver.findElement(By.xpath("//a[@aria-label='Sign in']")).click();
        try{Thread.sleep(5000);}
        catch(InterruptedException e){System.out.println(e);}
        driver.findElement(By.xpath("//input[@id='identifierId']")).sendKeys("awkh8073@gmail.com");
        driver.findElement(By.xpath("//span[text()='Next']")).click();
        try{Thread.sleep(5000);}
        catch(InterruptedException e){System.out.println(e);}
        driver.findElement(By.xpath("//input[@name='Passwd']")).sendKeys("ajmal");

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
    public void setAttributeValue(){
        // Locate the element
        WebElement element = driver.findElement(By.className("MV3Tnb"));

        // Initialize JavascriptExecutor
        JavascriptExecutor js = (JavascriptExecutor) driver;

        // Set the attribute value
        js.executeScript("arguments[0].setAttribute('value', 'new value');", element);

        // Wait for a few seconds (optional, for debugging purposes)
        try {
            Thread.sleep(5000); // Wait to observe the change (not recommended for production tests)
        } catch (InterruptedException e) {
            e.printStackTrace();
        }

        // Retrieve the attribute value after setting it
        String updatedValue = (String) js.executeScript("return arguments[0].getAttribute('value');", element);

        // Validate the attribute value
        Assert.assertEquals(updatedValue, "new value", "The attribute value was not updated as expected.");

    }


    @Test
   public void validateYouLandedOnThePage(){
    String title = driver.getTitle();
    assert title.contains("Google");
  }     
   
   @Test
   public void ValidateSearchField() {
    WebElement searchField = driver.findElement(By.id("APjFqb"));
    searchField.click();
    searchField.sendKeys("ajmal");
      driver.findElement(By.name("btnK")).click();
      try {
        Thread.sleep(5000); 
   } catch (InterruptedException e) {
        e.printStackTrace();
    }
   }

   @Test
   public void retrieveSearchHistory() {
     WebElement searchField = driver.findElement(By.id("APjFqb"));
     searchField.click();
    List<WebElement> searchHistory = driver.findElements(By.xpath("//div[@class='OBMEnb']//li"));
    for (WebElement history : searchHistory) {
        System.out.println("search history list: "+ history.getText());
        try {
            Thread.sleep(500); // 1 second delay between each print
        } catch (InterruptedException e) {
            e.printStackTrace();
        }
    }
    System.out.println("the first element in the search history list is: "+ searchHistory.get(0).getText());
    System.out.println("the meddle element in the search history list is: " + searchHistory.get(searchHistory.size()/2).getText());
    System.out.println("the last element in the search history list is: " + searchHistory.get(searchHistory.size()-1).getText());
} 

    @AfterMethod
    public void tearDown() {
        if (driver != null) {
            driver.quit();
        }
    }
}