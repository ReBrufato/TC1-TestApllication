package pageobjects;

import java.time.Duration;
import java.util.List;

import org.openqa.selenium.By;
import org.openqa.selenium.WebDriver;
import org.openqa.selenium.WebElement;
import org.openqa.selenium.support.ui.ExpectedConditions;
import org.openqa.selenium.support.ui.WebDriverWait;

public class IndexPage {
    private WebDriver driver;
    private final String url = "http://localhost:3000";

    private By registerBtnBy = By.xpath("/html/body/div[1]/div/div[2]/div[2]/div/div[1]/div/button");
    private By inputElementBy = By.className("sc-giAruI qqQdn");
    
    public IndexPage(WebDriver driver) {
        this.driver = driver;
    }

    public void registerClient(String name, String doc, String email, String phone) {
        driver.get(url);
        WebElement registerBtn = new WebDriverWait(driver, Duration.ofSeconds(10)).until(
                ExpectedConditions.elementToBeClickable(registerBtnBy));
        registerBtn.click();
        List<WebElement> inputElements = new WebDriverWait(driver, Duration.ofSeconds(5)).until(
            ExpectedConditions.presenceOfAllElementsLocatedBy(inputElementBy));
        inputElements.get(1).sendKeys(name);
        inputElements.get(2).sendKeys(doc);
        inputElements.get(3).sendKeys(email);
        inputElements.get(4).sendKeys(phone);
    }

}
