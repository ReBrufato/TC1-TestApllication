import static org.junit.jupiter.api.Assertions.assertEquals;

import java.io.File;
import java.time.Duration;

import org.junit.jupiter.api.AfterEach;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.DisplayName;
import org.junit.jupiter.api.Test;
import org.openqa.selenium.WebDriver;
import org.openqa.selenium.firefox.FirefoxDriver;
import org.openqa.selenium.firefox.FirefoxDriverService;
import org.openqa.selenium.firefox.FirefoxOptions;
import org.openqa.selenium.firefox.GeckoDriverService;

import com.github.javafaker.Faker;

import pageobjects.IndexPage;

public class Tests {

    
    private static File file = new File("src/test/resources/webdriver/geckodriver");
    private static WebDriver driver = null;
    private static Faker faker = new Faker();

    @BeforeEach
    public void getWebDriver() {
        if (driver == null) {
            FirefoxDriverService service = new GeckoDriverService.Builder().usingDriverExecutable(file).build();
            FirefoxOptions options = new FirefoxOptions();
            options.addArguments("-headless");
            driver = new FirefoxDriver(service, options);
        }
    }

    @AfterEach
    public void quitWebDriver() {
        if (driver != null) driver.quit();
    }

    @Test
    @DisplayName("shouldRegisterAClient")
    public void shouldRegisterAClient() {
        IndexPage indexPage = new IndexPage(driver);
        String name = faker.name().fullName();
        indexPage.registerClient(
            name,
            faker.idNumber().toString(),
            faker.internet().emailAddress(),
            faker.phoneNumber().toString());
        assertEquals(indexPage.findClient(name), name);
    }


}
