package pageobjects;

import java.time.Duration;
import java.util.List;

import dtos.PetDTO;
import org.openqa.selenium.By;
import org.openqa.selenium.ElementClickInterceptedException;
import org.openqa.selenium.Keys;
import org.openqa.selenium.WebDriver;
import org.openqa.selenium.WebElement;
import org.openqa.selenium.interactions.Actions;
import org.openqa.selenium.support.ui.ExpectedConditions;
import org.openqa.selenium.support.ui.FluentWait;
import org.openqa.selenium.support.ui.WebDriverWait;

public class PetPage {

    private WebDriver driver;
    private final String url = "http://localhost:3000/pets";
    private By linkToIndexPageBy = By.xpath("/html/body/div[1]/div/div[1]/div[2]/div/div/a[1]");
    private By inputElementsBy = By.className("sc-giAruI");
    private By inputSelectOwnerBy = By
            .xpath("/html/body/div[3]/div[2]/div/div/div[2]/div[4]/div[2]/div/div[1]/div[2]");
    private By registerBtnBy = By.xpath("/html/body/div[1]/div/div[2]/div[2]/div/div[1]/div/button");
    private By confirmBtnBy = By.xpath("/html/body/div[3]/div[2]/div/div/div[3]/button[2]");
    private By petTableBodyBy = By
            .xpath("/html/body/div[1]/div/div[2]/div[2]/div/div[2]/div/div[2]/div[2]/table/tbody");

    public PetPage(WebDriver driver) {
        this.driver = driver;
        driver.get(url);
    }

    public String getPageUrl() {
        return url;
    }

    public IndexPage goToIndexPage(WebDriver driver) {
         new FluentWait<>(driver).withTimeout(Duration.ofSeconds(10))
                        .pollingEvery(Duration.ofMillis(1000))
                        .ignoring(ElementClickInterceptedException.class)
                        .until(ExpectedConditions.elementToBeClickable(linkToIndexPageBy)).click();
        return new IndexPage(driver);
    }

    public void addPet(PetDTO petDTO) {
        WebElement registerBtn = new WebDriverWait(driver, Duration.ofSeconds(10))
                .until(ExpectedConditions.elementToBeClickable(registerBtnBy));
        registerBtn.click();
        setFormInputs(petDTO);
    }

    public void setFormInputs(PetDTO petDTO) {
        List<WebElement> inputElements = new WebDriverWait(driver, Duration.ofSeconds(5)).until(
                ExpectedConditions.presenceOfAllElementsLocatedBy(inputElementsBy));
        inputElements.get(0).sendKeys(petDTO.getName());
        inputElements.get(1).sendKeys(petDTO.getType());
        inputElements.get(2).sendKeys(petDTO.getBreed());
        WebElement inputSelectOwner = new WebDriverWait(driver, Duration.ofSeconds(10)).until(
                ExpectedConditions.visibilityOfElementLocated(inputSelectOwnerBy));
        inputSelectOwner.click();
        inputSelectOwner.findElement(By.tagName("input")).sendKeys(petDTO.getOwnerName());
        new Actions(driver).keyDown(Keys.ENTER).perform();
        WebElement confirmBtn = new WebDriverWait(driver, Duration.ofSeconds(10)).until(
                ExpectedConditions.presenceOfElementLocated(confirmBtnBy));
        confirmBtn.click();
    }

    public int getPetTableBodySize() {
        return getPetTableBodyRows().size();
    }

    private List<WebElement> getPetTableBodyRows() {
        WebElement tableBody = new WebDriverWait(driver, Duration.ofSeconds(5)).until(
                ExpectedConditions.presenceOfElementLocated(petTableBodyBy));
        return tableBody.findElements(By.tagName("tr"));
    }
}
