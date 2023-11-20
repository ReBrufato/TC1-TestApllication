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
    private final String url = "http://localhost:3000/";
    private By registerBtnBy = By.xpath("/html/body/div[1]/div/div[2]/div[2]/div/div[1]/div/button");
    private By inputElementsBy = By.className("sc-giAruI");
    private By confirmBtnBy = By.xpath("/html/body/div[3]/div[2]/div/div/div[3]/button[2]");
    private By linkToPetPageBy = By.xpath("/html/body/div[1]/div/div[1]/div[2]/div/div/a[2]");
    private By clientTableBodyBy = By.xpath("/html/body/div[1]/div/div[2]/div[2]/div/div[2]/div/div[2]/div[2]/table/tbody");

    public IndexPage(WebDriver driver) {
        this.driver = driver;
        driver.get(url);
    }

    public String getPageUrl() {
        return url;
    }

    public PetPage goToPetPage() {
        WebElement linkToPetPage = new WebDriverWait(driver, Duration.ofSeconds(5)).until(
                ExpectedConditions.presenceOfElementLocated(linkToPetPageBy));
        linkToPetPage.click();
        return new PetPage(driver);
    }

    public int getClientTableBodySize() {
        WebElement tableBody = new WebDriverWait(driver, Duration.ofSeconds(5)).until(
                ExpectedConditions.presenceOfElementLocated(clientTableBodyBy));
        return tableBody.findElements(By.tagName("tr")).size();
    }

    public void addClient(String name, String doc, String email, String phone) {
        WebElement registerBtn = new WebDriverWait(driver, Duration.ofSeconds(10)).until(
                ExpectedConditions.elementToBeClickable(registerBtnBy));
        registerBtn.click();
        List<WebElement> inputElements = new WebDriverWait(driver, Duration.ofSeconds(5)).until(
                ExpectedConditions.presenceOfAllElementsLocatedBy(inputElementsBy));
        inputElements.get(0).sendKeys(name);
        inputElements.get(1).sendKeys(doc);
        inputElements.get(2).sendKeys(email);
        inputElements.get(3).sendKeys(phone);
        WebElement confirmBtn = new WebDriverWait(driver, Duration.ofSeconds(10)).until(
                ExpectedConditions.presenceOfElementLocated(confirmBtnBy));
        confirmBtn.click();
    }

   

   

    

}
