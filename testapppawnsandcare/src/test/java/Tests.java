import static org.junit.jupiter.api.Assertions.assertEquals;

import java.io.File;
import java.time.Duration;
import static org.assertj.core.api.Assertions.*;
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

    // private File linuxFile = newFile("src/test/resources/webdriver/linux/geckodriver");
    private File file = new File("src/test/resources/webdriver/win/geckodriver.exe");
    private WebDriver driver = null;
    private Faker faker = new Faker();

    @BeforeEach
    public void getWebDriver() {
        if (driver == null) {
            FirefoxDriverService service = new GeckoDriverService.Builder().usingDriverExecutable(file).build();
            //FirefoxOptions options = new FirefoxOptions();
            //options.addArguments("-headless");
            //driver = new FirefoxDriver(service, options);
            driver = new FirefoxDriver(service);
        }
    }

   @AfterEach
    public void quitWebDriver() {
        if (driver != null)
            driver.quit();
    }

    @Test
    @DisplayName("Should register a client and search for his name in the page")
    public void shouldRegisterAClientAndSearchForHisNameInThePage() {
        IndexPage indexPage = new IndexPage(driver);
        String name = faker.name().fullName();
        
        indexPage.registerClient(
                name,
                faker.idNumber().toString(),
                faker.internet().emailAddress(),
                faker.phoneNumber().toString());
                
        assertThat(indexPage.isClientNameInThePage(name)).isTrue();
         
    }
}
