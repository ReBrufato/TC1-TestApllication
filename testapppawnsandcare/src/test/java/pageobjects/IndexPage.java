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

    public void registerClient(String name, String doc, String email, String phone) {
        driver.get(url);
        
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
        driver.get(url);

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

    public String getPagePet(){
        driver.get(url);

        final WebElement linkPet = new WebDriverWait(driver, Duration.ofSeconds(5))
                .until(ExpectedConditions.elementToBeClickable(By.xpath("/html/body/div[1]/div/div[1]/div[2]/div/div/a[2]")));
        linkPet.click();

        new WebDriverWait(driver, Duration.ofSeconds(5))
                .until(ExpectedConditions.titleIs("Paws & Care | Pets"));

        return driver.getTitle();
    }

    public String getPageClientes(){
        driver.get(url + "pets");

        final WebElement linkClient = new WebDriverWait(driver, Duration.ofSeconds(5))
                .until(ExpectedConditions.elementToBeClickable(By.xpath("/html/body/div[1]/div/div[1]/div[2]/div/div/a[1]")));
        linkClient.click();

        new WebDriverWait(driver, Duration.ofSeconds(5))
                .until(ExpectedConditions.titleIs("Paws & Care | Clientes"));

        return driver.getTitle();
    }

    public Integer addClient(String name, String doc, String email, String tel){
        driver.get(url);

        final WebElement buttonAddClient = new WebDriverWait(driver, Duration.ofSeconds(5))
                .until(ExpectedConditions.elementToBeClickable(By.xpath("/html/body/div[1]/div/div[2]/div[2]/div/div[1]/div/button")));

        //verify how many lines had before
        WebElement tableBody = driver.findElement(By.xpath("/html/body/div[1]/div/div[2]/div[2]/div/div[2]/div/div[2]/div[2]/table/tbody"));
        Integer numberRowsBefore = tableBody.findElements(By.tagName("tr")).size();

        buttonAddClient.click();

        new WebDriverWait(driver, Duration.ofSeconds(5))
                .until(ExpectedConditions.visibilityOfElementLocated(By.xpath("/html/body/div[3]/div[2]/div/div")));

        driver.findElement(By.xpath("/html/body/div[3]/div[2]/div/div/div[2]/div[1]/div[2]/input")).sendKeys(name);
        driver.findElement(By.xpath("/html/body/div[3]/div[2]/div/div/div[2]/div[2]/div[2]/input")).sendKeys(doc);
        driver.findElement(By.xpath("//*[@id=\"portal-modal\"]/div[2]/div/div/div[2]/div[3]/div[1]/div[2]/input")).sendKeys(email);
        driver.findElement(By.xpath("/html/body/div[3]/div[2]/div/div/div[2]/div[3]/div[2]/div[2]/input")).sendKeys(tel);

        driver.findElement(By.xpath("/html/body/div[3]/div[2]/div/div/div[3]/button[2]")).click();

        //verified if row add
        Integer numberRowsAfter = tableBody.findElements(By.tagName("tr")).size();

        return numberRowsAfter - numberRowsBefore;
    }

}
