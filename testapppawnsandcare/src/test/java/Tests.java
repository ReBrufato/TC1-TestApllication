
import static org.assertj.core.api.Assertions.*;
import org.junit.jupiter.api.AfterEach;
import org.junit.jupiter.api.BeforeAll;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.DisplayName;
import org.junit.jupiter.api.Test;
import org.openqa.selenium.WebDriver;
import org.openqa.selenium.firefox.FirefoxDriver;

import com.github.javafaker.Faker;

import io.github.bonigarcia.wdm.WebDriverManager;
import pageobjects.IndexPage;
import pageobjects.PetPage;

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
    @DisplayName("Should access the pet page from index page")
    void shouldAccessThePetPageFromIndexPage() {
        IndexPage indexPage = new IndexPage(driver);
        PetPage petPage = indexPage.goToPetPage();
        assertThat(petPage.getPageUrl()).isEqualTo(driver.getCurrentUrl());
    }

    @Test
    @DisplayName("Should access the index page from pet page")
    void shouldAccessTheIndexPageFromPetPage() {
        PetPage petPage = new PetPage(driver);
        IndexPage indexPage = petPage.goToIndexPage(driver);
        assertThat(indexPage.getPageUrl()).isEqualTo(driver.getCurrentUrl());
    }

   
}
