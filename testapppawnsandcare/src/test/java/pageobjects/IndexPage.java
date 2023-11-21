package pageobjects;

import java.time.Duration;
import java.util.List;

import org.openqa.selenium.By;
import org.openqa.selenium.ElementClickInterceptedException;
import org.openqa.selenium.WebDriver;
import org.openqa.selenium.WebElement;
import org.openqa.selenium.support.ui.ExpectedConditions;
import org.openqa.selenium.support.ui.FluentWait;
import org.openqa.selenium.support.ui.WebDriverWait;

import dtos.ClientDTO;

public class IndexPage {
        private WebDriver driver;
        private final String url = "http://localhost:3000/";
        private By registerBtnBy = By.xpath("/html/body/div[1]/div/div[2]/div[2]/div/div[1]/div/button");
        private By inputElementsBy = By.className("sc-giAruI");
        private By confirmBtnBy = By.xpath("/html/body/div[3]/div[2]/div/div/div[3]/button[2]");
        private By linkToPetPageBy = By.xpath("/html/body/div[1]/div/div[1]/div[2]/div/div/a[2]");
        private By clientTableBodyBy = By
                        .xpath("/html/body/div[1]/div/div[2]/div[2]/div/div[2]/div/div[2]/div[2]/table/tbody");
       

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
                return getClientTableBodyRows().size();
        }

        public void addClient(ClientDTO clientDTO) {
                WebElement registerBtn = new WebDriverWait(driver, Duration.ofSeconds(10)).until(
                                ExpectedConditions.elementToBeClickable(registerBtnBy));
                registerBtn.click();
                setFormInputs(clientDTO);
        }

        public void deleteClient() {
                WebElement delBtn = getClientTableBodyRows().get(getClientTableBodySize() - 1)
                                .findElements(By.tagName("button")).get(1);
                new FluentWait<>(driver).withTimeout(Duration.ofSeconds(10))
                        .pollingEvery(Duration.ofMillis(500))
                        .ignoring(ElementClickInterceptedException.class)
                        .until(ExpectedConditions.elementToBeClickable(delBtn)).click();
                new WebDriverWait(driver, Duration.ofSeconds(10)).until(
                                ExpectedConditions.elementToBeClickable(confirmBtnBy)).click();
        }

        public void editClient(ClientDTO clientDTO) {
                WebElement editBtn = getClientTableBodyRows().get(getClientTableBodySize() - 1)
                                .findElements(By.tagName("button")).get(0);
                new FluentWait<>(driver).withTimeout(Duration.ofSeconds(10))
                        .pollingEvery(Duration.ofMillis(500))
                        .ignoring(ElementClickInterceptedException.class)
                        .until(ExpectedConditions.elementToBeClickable(editBtn)).click();
                cleanFormInputs();
                setFormInputs(clientDTO);
        }

        public ClientDTO getDataOfAddedClient() {
                List<WebElement> thElements = getClientTableBodyRows().get(getClientTableBodySize() - 1)
                                .findElements(By.cssSelector("th span"));
                return new ClientDTO(
                                thElements.get(1).getText(),
                                thElements.get(2).getText()
                                        .replace("-", "")
                                        .replace("/", "")
                                        .replace(".", ""),
                                thElements.get(3).getText());
        }

        private List<WebElement> getClientTableBodyRows() {
                WebElement tableBody = new WebDriverWait(driver, Duration.ofSeconds(5)).until(
                                ExpectedConditions.presenceOfElementLocated(clientTableBodyBy));
                return tableBody.findElements(By.tagName("tr"));
        }

        private void setFormInputs(ClientDTO clientDTO) {
                List<WebElement> inputElements = new WebDriverWait(driver, Duration.ofSeconds(5)).until(
                                ExpectedConditions.presenceOfAllElementsLocatedBy(inputElementsBy));
                inputElements.get(0).sendKeys(clientDTO.getName());
                inputElements.get(1).sendKeys(clientDTO.getDoc());
                inputElements.get(2).sendKeys(clientDTO.getEmail());
                inputElements.get(3).sendKeys(clientDTO.getPhone());
                WebElement confirmBtn = new WebDriverWait(driver, Duration.ofSeconds(10)).until(
                                ExpectedConditions.presenceOfElementLocated(confirmBtnBy));
                confirmBtn.click();
        }

        private void cleanFormInputs() {
                List<WebElement> inputElements = new WebDriverWait(driver, Duration.ofSeconds(5)).until(
                                ExpectedConditions.presenceOfAllElementsLocatedBy(inputElementsBy));
                inputElements.get(0).clear();
                inputElements.get(1).clear();
                inputElements.get(2).clear();
                inputElements.get(3).clear();
        }

}
