import java.io.File;
import java.time.Duration;

import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.DisplayName;
import org.junit.jupiter.api.Test;
import org.openqa.selenium.WebDriver;
import org.openqa.selenium.firefox.FirefoxDriver;
import org.openqa.selenium.firefox.FirefoxDriverService;
import org.openqa.selenium.firefox.FirefoxOptions;
import org.openqa.selenium.firefox.GeckoDriverService;

public class Tests {

    
    private static File file = new File("src/test/resources/webdriver/geckodriver");
    private static WebDriver driver = null;

    @BeforeEach
    public void getWebDriver() {
        if (driver == null) {
            FirefoxDriverService service = new GeckoDriverService.Builder().usingDriverExecutable(file).build();
            FirefoxOptions options = new FirefoxOptions();
            options.addArguments("-headless");
            driver = new FirefoxDriver(service, options);
        }
    }




}
