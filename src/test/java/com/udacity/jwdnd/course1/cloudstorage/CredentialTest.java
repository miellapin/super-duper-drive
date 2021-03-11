package com.udacity.jwdnd.course1.cloudstorage;

import com.udacity.jwdnd.course1.cloudstorage.entity.Credentials;
import com.udacity.jwdnd.course1.cloudstorage.pages.CredentialsPage;
import com.udacity.jwdnd.course1.cloudstorage.pages.LoginPage;
import com.udacity.jwdnd.course1.cloudstorage.pages.SignUpPage;
import com.udacity.jwdnd.course1.cloudstorage.services.CredentialService;
import io.github.bonigarcia.wdm.WebDriverManager;
import org.junit.jupiter.api.*;
import org.openqa.selenium.By;
import org.openqa.selenium.WebDriver;
import org.openqa.selenium.WebElement;
import org.openqa.selenium.chrome.ChromeDriver;
import org.openqa.selenium.support.ui.WebDriverWait;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.boot.web.server.LocalServerPort;

import java.util.List;


@SpringBootTest(webEnvironment = SpringBootTest.WebEnvironment.RANDOM_PORT)
public class CredentialTest {
    @LocalServerPort
    private int port;

    private static WebDriver webDriver;

    private static LoginPage loginPage;

    private static CredentialsPage credentialsPage;

    private static SignUpPage signUpPage;

    private static WebDriverWait webDriverWait;

    @Autowired
    private CredentialService credentialService;

    @BeforeAll
    static void beforeAll() {
        WebDriverManager.chromedriver().setup();
        webDriver = new ChromeDriver();
        webDriverWait = new WebDriverWait(webDriver, 2);
    }

    @BeforeEach
    public void beforeEach() {
        signUpPage = new SignUpPage(webDriver);
        loginPage = new LoginPage(webDriver);
        credentialsPage = new CredentialsPage(webDriver);
    }

    @AfterEach
    public void afterEach() {
        if (webDriver != null) {
            webDriver.quit();
        }
    }

    @Test
    public void addAndDeleteCredentialTest() {
        Credentials credential = credentialService.getAllCredentials().stream().filter(c ->
                c.getUrl().equalsIgnoreCase("twitter.com")).findFirst().orElse(null);
        Assertions.assertNull(credential);
        String username = "Berk";
        String password = "Yasar";
        webDriver.get("http://localhost:" + this.port + "/signup");
        signUpPage.signUpUser(username, password);
        webDriver.get("http://localhost:" + this.port + "/login");
        loginPage.loginUser(username, password);

        WebElement inputUserName = webDriverWait.until(wd -> wd.findElement(By.id("nav-credentials-tab")));
        Assertions.assertNotNull(inputUserName);

        credentialsPage.makeSureRightCredentialsTabIsActive();
        credentialsPage.clickOnAddNewCredentialsButton();

        credentialsPage.changeModalCredentialsURL("twitter.com");
        credentialsPage.changeModalCredentialsUsername("Veridis quo");
        credentialsPage.changeModalCredentialsPassword("123456");
        credentialsPage.saveModalChangesButton();

        credential = credentialService.getAllCredentials().stream().filter(c ->
                c.getUrl().equalsIgnoreCase("twitter.com")).findFirst().orElse(null);
        Assertions.assertNotNull(credential);

        credentialsPage.makeSureRightCredentialsTabIsActive();
        credentialsPage.clickOnDeleteCredentialsButton();
        credential = credentialService.getAllCredentials().stream().filter(c ->
                c.getUrl().equalsIgnoreCase("twitter.com")).findFirst().orElse(null);
        Assertions.assertNull(credential);
    }

    @Test
    public void editCredentialsTest() {
        String username = "Berk";
        String password = "Yasar";
        webDriver.get("http://localhost:" + this.port + "/signup");
        signUpPage.signUpUser(username, password);
        webDriver.get("http://localhost:" + this.port + "/login");
        loginPage.loginUser(username, password);
        credentialsPage.makeSureRightCredentialsTabIsActive();
        credentialsPage.clickOnAddNewCredentialsButton();

        credentialsPage.changeModalCredentialsURL("twitter.com");
        credentialsPage.changeModalCredentialsUsername("Veridis quo");
        credentialsPage.changeModalCredentialsPassword("123456");
        credentialsPage.saveModalChangesButton();

        credentialsPage.makeSureRightCredentialsTabIsActive();
        credentialsPage.clickOnCredentialsEditButton();
        credentialsPage.changeModalCredentialsURL("edited url");
        credentialsPage.saveModalChangesButton();

        Credentials credential = credentialService.getAllCredentials().stream().filter(c ->
                c.getUrl().equalsIgnoreCase("edited url")).findFirst().orElse(null);
        Assertions.assertNotNull(credential);
    }
}
