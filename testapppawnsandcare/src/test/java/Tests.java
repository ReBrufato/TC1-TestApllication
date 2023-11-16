import java.io.File;

import org.junit.jupiter.api.DisplayName;
import org.junit.jupiter.api.Test;
import org.openqa.selenium.WebDriver;
import org.openqa.selenium.firefox.FirefoxDriver;
import org.openqa.selenium.firefox.FirefoxDriverService;
import org.openqa.selenium.firefox.GeckoDriverService;

public class Tests {

    @Test
    @DisplayName("Testing the webdriver")
    public void testingTheWebdriver() throws InterruptedException {
        File file = new File("src/test/resources/webdriver/geckodriver");
        FirefoxDriverService service = new GeckoDriverService.Builder().usingDriverExecutable(file).build();
        WebDriver driver = new FirefoxDriver(service);
        driver.get("http://localhost:3000"); 
        Thread.sleep(1000);
        driver.quit(); 
    }

}
