package pageobjects;

import java.time.Duration;

import org.openqa.selenium.By;
import org.openqa.selenium.WebDriver;
import org.openqa.selenium.WebElement;
import org.openqa.selenium.support.ui.ExpectedConditions;
import org.openqa.selenium.support.ui.WebDriverWait;

public class PetPage {
    
    private WebDriver driver;
    private final String url = "http://localhost:3000/pets"; 

    private By linkToIndexPageBy = By.xpath("/html/body/div[1]/div/div[1]/div[2]/div/div/a[1]");

    public PetPage(WebDriver driver) {
        this.driver = driver;
        driver.get(url);
    }

    public String getPageUrl() {
        return url;
    }

    public IndexPage goToIndexPage(WebDriver driver) {
        WebElement linkToIndexPage = new WebDriverWait(driver, Duration.ofSeconds(5)).until(
                ExpectedConditions.presenceOfElementLocated(linkToIndexPageBy));
        linkToIndexPage.click();
        return new IndexPage(driver);
    }
}
