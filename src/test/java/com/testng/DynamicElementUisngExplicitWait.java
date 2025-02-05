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

   @Test
   public void retrieveSearchHistory(){
    List <WebElement> searchHistory = driver.findElements(By.xpath("//div[@class="OBMEnb"]//li"));
    for(WebElement history : searchHistory){
        System.out.println(history.getText());
        // i want to get print first middle and last element text from the search history
        if(history.getText().equals(searchHistory.get(0).getText()) || history.getText().equals(searchHistory.get(searchHistory.size()/2).getText()) || history.getText().equals(searchHistory.get(searchHistory.size()-1).getText())){
            System.out.println(history.getText());
    }
   }

    @AfterMethod
    public void tearDown() {
        if (driver != null) {
            driver.quit();
        }
    }
}