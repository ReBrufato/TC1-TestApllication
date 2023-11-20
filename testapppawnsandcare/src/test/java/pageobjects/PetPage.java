package pageobjects;

import org.openqa.selenium.WebDriver;

public class PetPage {
    
    private WebDriver driver;
    private final String url = "http://localhost:3000/pets"; 

    public PetPage(WebDriver driver) {
        this.driver = driver;
        driver.get(url);
    }

    public String getPageUrl() {
        return url;
    }
}
