
import static org.assertj.core.api.Assertions.*;

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
        @DisplayName("Should allow to add a client using an invalid email")
        public void shouldAllowToAddAClientUsingAnInvalidEMail() {
            IndexPage indexPage = new IndexPage(driver);
            int tableSizeBefore = indexPage.getClientTableBodySize();
            indexPage.addClient(
                    new ClientDTO(
                            faker.name().fullName(),
                            faker.idNumber().valid(),
                            "invalid_email",
                            faker.phoneNumber().phoneNumber()));
            assertThat(indexPage.getClientTableBodySize()).isGreaterThan(tableSizeBefore);
        }

        @Test
        @DisplayName("Should allow to add a client using a doc id without a pattern")
        public void shouldAllowToAddAClientUsingDocIdWithoutPattern() {
            IndexPage indexPage = new IndexPage(driver);
            int tableSizeBefore = indexPage.getClientTableBodySize();
            indexPage.addClient(
                    new ClientDTO(
                            faker.name().fullName(),
                            "1",
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

        @Test
        @DisplayName("Should not allow to add a client with empty name")
        public void shouldNotAllowToAddAClientWithEmptyName() {
            IndexPage indexPage = new IndexPage(driver);
            indexPage.addClient(
                    new ClientDTO(
                            "",
                            faker.idNumber().valid(),
                            faker.internet().emailAddress(),
                            faker.phoneNumber().phoneNumber()));
            assertThat(indexPage.getErrorMessage()).isEqualTo("O parametro \"name\" está faltando");
        }

        @Test
        @DisplayName("Should not allow to add a client with empty email")
        public void shouldNotAllowToAddAClientWithEmptyEmail() {
            IndexPage indexPage = new IndexPage(driver);
            indexPage.addClient(
                    new ClientDTO(
                            faker.name().fullName(),
                            faker.idNumber().valid(),
                            "",
                            faker.phoneNumber().phoneNumber()));
            assertThat(indexPage.getErrorMessage()).isEqualTo("O parametro \"email\" está faltando");
        }

        @Test
        @DisplayName("Should not allow to add a client with empty doc")
        public void shouldNotAllowToAddAClientWithEmptyDoc() {
            IndexPage indexPage = new IndexPage(driver);
            indexPage.addClient(
                    new ClientDTO(
                            faker.name().fullName(),
                            "",
                            faker.internet().emailAddress(),
                            faker.phoneNumber().phoneNumber()));
            assertThat(indexPage.getErrorMessage()).isEqualTo("O parametro \"document\" está faltando");
        }

        @Test
        @DisplayName("Should not allow to add a client with doc contaning characters different from numbers")
        public void shouldNotAllowToAddAClientWithDocContaningCharactersDifferentFromNumbers() {
            IndexPage indexPage = new IndexPage(driver);
            indexPage.addClient(
                    new ClientDTO(
                            faker.name().fullName(),
                            faker.lorem().sentence(),
                            faker.internet().emailAddress(),
                            faker.phoneNumber().phoneNumber()));
            assertThat(indexPage.getErrorMessage()).isEqualTo("O parametro \"document\" está faltando");
        }

        @Test
        @DisplayName("Should not allow to add a client with empty phone")
        public void shouldNotAllowToAddAClientWithEmptyPhone() {
            IndexPage indexPage = new IndexPage(driver);
            indexPage.addClient(
                    new ClientDTO(
                            faker.name().fullName(),
                            faker.idNumber().valid(),
                            faker.internet().emailAddress(),
                            ""));
            assertThat(indexPage.getErrorMessage()).isEqualTo("O parametro \"phone\" está faltando");
        }

        @Test
        @DisplayName("Should not allow to add a client with phone containg characters different from numbers")
        public void shouldNotAllowToAddAClientWithPhoneContaingCharactersDifferentFromNumbers() {
            IndexPage indexPage = new IndexPage(driver);
            indexPage.addClient(
                    new ClientDTO(
                            faker.name().fullName(),
                            faker.idNumber().valid(),
                            faker.internet().emailAddress(),
                            faker.lorem().sentence()));
            assertThat(indexPage.getErrorMessage()).isEqualTo("O parametro \"phone\" está faltando");
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

            PetPage petPage = new PetPage(driver); // indexPage.goToPetPage(); it is not possible to use this method
                                                   // because the modal container obscures the link element, even using
                                                   // a fluent wait
            int tableSizeBefore = petPage.getPetTableBodySize();
            PetDTO petDTO = new PetDTO(
                    faker.cat().name(),
                    "Gato",
                    faker.cat().breed(),
                    clientDTO.getName());
            petPage.addPet(petDTO);
            assertThat(petPage.getPetTableBodySize()).isGreaterThan(tableSizeBefore);
        }

        @Test
        @DisplayName("Should add a client, a pet and edit the pet data after it")
        void shouldAddAClientAPetAndEditThePetDataAfterIt() {
            IndexPage indexPage = new IndexPage(driver);
            ClientDTO clientDTO = new ClientDTO(
                    faker.name().fullName(),
                    faker.idNumber().valid(),
                    faker.internet().emailAddress(),
                    faker.phoneNumber().phoneNumber());
            indexPage.addClient(clientDTO);

            PetPage petPage = new PetPage(driver); // indexPage.goToPetPage(); it is not possible to use this method
                                                   // because the modal container obscures the link element, even using
                                                   // a fluent wait
            PetDTO petDTO = new PetDTO(
                    faker.cat().name(),
                    "Gato",
                    faker.cat().breed(),
                    clientDTO.getName());
            petPage.addPet(petDTO);

            PetDTO newPet = new PetDTO(
                    faker.cat().name(),
                    "Gato",
                    faker.cat().breed(),
                    clientDTO.getName());
            petPage.editPet(newPet);
            assertThat(newPet).isEqualTo(petPage.getDataOfAddedPet());
        }

        @Test
        @DisplayName("Should not allow to add a pet if do not has a client registered")
        void shouldNotAllowAddAPetIfDoNotHasAClientRegistered() {
            PetPage petPage = new PetPage(driver);
            PetDTO petDTO = new PetDTO(
                    faker.cat().name(),
                    "Gato",
                    faker.cat().breed(),
                    "");
            petPage.addPet(petDTO);
            assertThat(petPage.getErrorMessage()).isEqualTo("O parametro \"Dono\" está faltando");
        }

        @Test
        @DisplayName("Should add a client, a pet and delete the pet data after it")
        void shouldAddAClientAPetAndDeleteThePetDataAfterIt() {
            IndexPage indexPage = new IndexPage(driver);
            ClientDTO clientDTO = new ClientDTO(
                    faker.name().fullName(),
                    faker.idNumber().valid(),
                    faker.internet().emailAddress(),
                    faker.phoneNumber().phoneNumber());
            indexPage.addClient(clientDTO);

            PetPage petPage = new PetPage(driver); // indexPage.goToPetPage(); it is not possible to use this method
                                                   // because the modal container obscures the link element, even using
                                                   // a fluent wait
            PetDTO petDTO = new PetDTO(
                    faker.cat().name(),
                    "Gato",
                    faker.cat().breed(),
                    clientDTO.getName());
            petPage.addPet(petDTO);
            petPage.deletePet();
            assertThat(petPage.getPetTableBodySize()).isZero();
        }

        @Test
        @DisplayName("Should not allow to delete a client related with a pet")
        public void shouldNotAllowToDeleteAClientRelatedWithAPet() {
            IndexPage indexPage = new IndexPage(driver);
            ClientDTO clientDTO = new ClientDTO(
                    faker.name().fullName(),
                    faker.idNumber().valid(),
                    faker.internet().emailAddress(),
                    faker.phoneNumber().phoneNumber());
            indexPage.addClient(clientDTO);

            PetPage petPage = new PetPage(driver); // indexPage.goToPetPage(); it is not possible to use this method
                                                   // because the modal container obscures the link element, even using
                                                   // a fluent wait
            PetDTO petDTO = new PetDTO(
                    faker.cat().name(),
                    "Gato",
                    faker.cat().breed(),
                    clientDTO.getName());
            petPage.addPet(petDTO);
            indexPage = indexPage.reloadThePage();// indexPage.goToPetPage(); it is not possible to use this method
                                                  // because the modal container obscures the link element, even using
                                                  // a fluent wait
            indexPage.deleteClient();
            petPage = petPage.reloadThePage();
            assertThat(petPage.getPetTableBodySize()).isGreaterThan(0);

        }

    }

}
