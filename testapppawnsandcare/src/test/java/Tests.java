import static org.junit.jupiter.api.Assertions.assertEquals;

import java.io.File;
import java.time.Duration;
import static org.assertj.core.api.Assertions.*;
import org.junit.jupiter.api.AfterEach;
import org.junit.jupiter.api.BeforeAll;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.DisplayName;
import org.junit.jupiter.api.Test;
import org.openqa.selenium.WebDriver;
import org.openqa.selenium.firefox.FirefoxDriver;
import org.openqa.selenium.firefox.FirefoxDriverService;
import org.openqa.selenium.firefox.FirefoxOptions;

import com.github.javafaker.Faker;

import io.github.bonigarcia.wdm.WebDriverManager;
import pageobjects.IndexPage;

public class Tests {

    private WebDriver driver;
    private Faker faker = new Faker();

    @BeforeAll
    public static void setupWebDriver() {
        WebDriverManager.firefoxdriver().setup();
    }

    @BeforeEach
    public void getWebDriver() {
       driver = new FirefoxDriver();
    }

   @AfterEach
    public void quitWebDriver() {
            driver.quit();
    }

    @Test
    @DisplayName("shouldPerformRequisitionToApp")
    void testGetToApp() throws InterruptedException {
        driver.get("http://localhost:3000/");
        Thread.sleep(1500);
    }

    @Test
    @DisplayName("shouldChangeToPagePet")
    void shouldChangeToPagePet(){
        IndexPage indexPage = new IndexPage(driver);
        assertThat(indexPage.getPagePet()).isEqualTo("Paws & Care | Pets");
    }

    @Test
    @DisplayName("shouldChangeToPageClients")
    void shouldChangeToPageClients() {
        IndexPage indexPage = new IndexPage(driver);
        assertThat(indexPage.getPageClientes()).isEqualTo("Paws & Care | Clientes");
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
