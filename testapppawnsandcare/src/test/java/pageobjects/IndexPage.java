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
    private By searchInputBy = By.xpath("/html/body/div[1]/div/div[2]/div[2]/div/div[2]/div/div[1]/div/div[2]/input");
    private By searchBtnBy = By.xpath("/html/body/div[1]/div/div[2]/div[2]/div/div[2]/div/div[1]/button");
    private By registerBtnBy = By.xpath("/html/body/div[1]/div/div[2]/div[2]/div/div[1]/div/button");
    private By inputElementsBy = By.className("sc-giAruI");
    private By nameInTableBy = By
            .xpath("/html/body/div[1]/div/div[2]/div[2]/div/div[2]/div/div[2]/div[2]/table/tbody/tr/th[2]/span");
    private By confirmBtnBy = By.xpath("/html/body/div[3]/div[2]/div/div/div[3]/button[2]");
    private By tableBy = By.xpath("url");
    private By clientNamesBy = By.className("kPiqhn");
    private By coverLayerBy = By.className("sc-iCoHzw");

    public IndexPage(WebDriver driver) {
        this.driver = driver;
        driver.get(url);
    }

    public void registerClient(String name, String doc, String email, String phone) {
        
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

    public Boolean isClientNameInThePage(String name) {
        try {
            return name.equals(findClientName(name));
        } catch (Exception e) {
            return false;
        }
    }

    private String findClientName(String name) {

        WebElement searchInput = new WebDriverWait(driver, Duration.ofSeconds(5)).until(
                ExpectedConditions.visibilityOfElementLocated(searchInputBy));
        searchInput.sendKeys(name);
        WebElement searchBtn = new WebDriverWait(driver, Duration.ofSeconds(5)).until(
                ExpectedConditions.elementToBeClickable(searchBtnBy));
        searchBtn.click();
        WebElement nameInTable = new WebDriverWait(driver, Duration.ofSeconds(5)).until(
                ExpectedConditions.visibilityOfElementLocated(nameInTableBy));
        return nameInTable.getText();
    }

}
