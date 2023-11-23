
import static org.assertj.core.api.Assertions.*;
import static org.junit.jupiter.api.Assertions.*;
import static org.junit.jupiter.api.Assertions.fail;

import dtos.PetDTO;
import org.junit.jupiter.api.*;
import org.openqa.selenium.WebDriver;
import org.openqa.selenium.firefox.FirefoxDriver;

import com.github.javafaker.Faker;

import dtos.ClientDTO;
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

    @Nested
    @DisplayName("Tests IndexPage")
    class IndexPageTests {
        @Test
        @DisplayName("Should access the pet page from index page")
        void shouldAccessThePetPageFromIndexPage() {
            IndexPage indexPage = new IndexPage(driver);
            PetPage petPage = indexPage.goToPetPage();
            assertThat(petPage.getPageUrl()).isEqualTo(driver.getCurrentUrl());
        }

        @Test
        @DisplayName("Should add a client")
        public void shouldAddAClient() {
            IndexPage indexPage = new IndexPage(driver);
            int tableSizeBefore = indexPage.getClientTableBodySize();
            indexPage.addClient(
                    new ClientDTO(
                            faker.name().fullName(),
                            faker.idNumber().valid(),
                            faker.internet().emailAddress(),
                            faker.phoneNumber().phoneNumber()));
            assertThat(indexPage.getClientTableBodySize()).isGreaterThan(tableSizeBefore);
        }

        @Test
        @DisplayName("Should add a client and edit your data")
        public void shouldAddAClientAndEditYourData() {
            IndexPage indexPage = new IndexPage(driver);
            ClientDTO inicialClientData = new ClientDTO(
                    faker.name().fullName(),
                    faker.idNumber().toString(),
                    faker.internet().emailAddress(),
                    faker.phoneNumber().toString());
            indexPage.addClient(inicialClientData);
            ClientDTO newClientData = new ClientDTO(
                    faker.name().fullName(),
                    faker.number().digits(10),
                    faker.internet().emailAddress(),
                    faker.phoneNumber().phoneNumber());
            indexPage.editClient(newClientData);
            assertThat(newClientData).isEqualTo(indexPage.getDataOfAddedClient());
        }

        @Test
        @DisplayName("Should add a client and delete it")
        public void shouldAddAClientAndDeleteIt() {
            IndexPage indexPage = new IndexPage(driver);
            indexPage.addClient(
                    new ClientDTO(
                            faker.name().fullName(),
                            faker.idNumber().valid(),
                            faker.internet().emailAddress(),
                            faker.phoneNumber().phoneNumber()));
            indexPage.deleteClient();
            assertThat(indexPage.getClientTableBodySize()).isZero();
        }
    }

    @Nested
    @DisplayName("Tests PetPage")
    class PetPageTests {
        @Test
        @DisplayName("Should access the index page from pet page")
        void shouldAccessTheIndexPageFromPetPage() {
            PetPage petPage = new PetPage(driver);
            IndexPage indexPage = petPage.goToIndexPage(driver);
            assertThat(indexPage.getPageUrl()).isEqualTo(driver.getCurrentUrl());
        }

        @Test
        @DisplayName("Should add a client and a pet")
        void shouldAddAClientAndAPet() {
            IndexPage indexPage = new IndexPage(driver);
            ClientDTO clientDTO = new ClientDTO(
                    faker.name().fullName(),
                    faker.idNumber().valid(),
                    faker.internet().emailAddress(),
                    faker.phoneNumber().phoneNumber());
            indexPage.addClient(clientDTO);

            PetPage petPage = new PetPage(driver); //indexPage.goToPetPage(); it is not possible to use this method because the modal container obscures the link element, even using a fluent wait
            int tableSizeBefore = petPage.getPetTableBodySize();
            PetDTO petDTO = new PetDTO(
                    faker.cat().name(),
                    "Gato",
                    faker.cat().breed(),
                    clientDTO.getName());
            petPage.addPet(petDTO);
            assertThat(petPage.getPetTableBodySize()).isGreaterThan(tableSizeBefore);
        }

        /*
         * @Test
         * 
         * @DisplayName("Should not add pet if number of clients is zero")
         * void shouldNotAddPetIfNumberOfClientsIsZero() {
         * PetPage petPage = new PetPage(driver);
         * boolean verify = petPage.addPet(new PetDTO(
         * faker.name().name(),
         * faker.cat().name(),
         * faker.cat().breed(),
         * ""));
         * 
         * assertThat(verify).isFalse();
         * }
         */
    }

}
