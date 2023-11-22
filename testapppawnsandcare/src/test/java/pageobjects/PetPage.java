package pageobjects;

import java.time.Duration;
import java.util.List;

import dtos.PetDTO;
import org.openqa.selenium.By;
import org.openqa.selenium.WebDriver;
import org.openqa.selenium.WebElement;
import org.openqa.selenium.support.ui.ExpectedConditions;
import org.openqa.selenium.support.ui.Select;
import org.openqa.selenium.support.ui.WebDriverWait;

public class PetPage {
    
    private WebDriver driver;
    private final String url = "http://localhost:3000/pets";

    private By linkToIndexPageBy = By.xpath("/html/body/div[1]/div/div[1]/div[2]/div/div/a[1]");

    private By msgErrorBy = By.xpath("/html/body/div[3]/div[4]/div/div/div[1]/h6");

    private By inputNameBy = By.xpath("/html/body/div[3]/div[2]/div/div/div[2]/div[1]/div[2]/input");

    private By inputTypeBy = By.xpath("/html/body/div[3]/div[2]/div/div/div[2]/div[2]/div[2]/input");

    private By inputBreedBy = By.xpath("/html/body/div[3]/div[2]/div/div/div[2]/div[3]/div[2]/input");

    private By selectOwnerBy = By.xpath("/html/body/div[3]/div[2]/div/div/div[2]/div[4]/div[2]/div/div[1]/div[2]/input");

    private By registerBtnBy = By.xpath("/html/body/div[1]/div/div[2]/div[2]/div/div[1]/div/button");

    private By confirmBtnBy = By.xpath("/html/body/div[3]/div[2]/div/div/div[3]/button[2]");



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

    public boolean addPet(PetDTO petDTO){
        WebElement registerBtn = new WebDriverWait(driver, Duration.ofSeconds(10))
                .until(ExpectedConditions.elementToBeClickable(registerBtnBy));
        registerBtn.click();

        boolean verify = setFormInputs(petDTO);

        WebElement msgError = new WebDriverWait(driver, Duration.ofSeconds(5)).until(
                ExpectedConditions.visibilityOfElementLocated(msgErrorBy));

        if (msgError.isDisplayed() || verify == false) return false;
        return true;
    }

    public boolean setFormInputs(PetDTO petDTO){
        driver.findElement(inputNameBy).sendKeys(petDTO.getName());
        driver.findElement(inputTypeBy).sendKeys(petDTO.getType());
        driver.findElement(inputBreedBy).sendKeys(petDTO.getBreed());

        WebElement owner = validateOwner();

        if (owner == null) return false;

        WebElement confirmBtn = new WebDriverWait(driver, Duration.ofSeconds(5)).until(
                ExpectedConditions.elementToBeClickable(confirmBtnBy));
        confirmBtn.click();

        return true;
    }

    public WebElement validateOwner(){
        WebElement element = driver.findElement(selectOwnerBy);
        Select select = new Select(element);

        select.selectByIndex(0);

        return select.getFirstSelectedOption();
    }
}
