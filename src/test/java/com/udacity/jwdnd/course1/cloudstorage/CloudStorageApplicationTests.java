package com.udacity.jwdnd.course1.cloudstorage;

import io.github.bonigarcia.wdm.WebDriverManager;
import org.junit.jupiter.api.*;
import org.openqa.selenium.WebDriver;
import org.openqa.selenium.chrome.ChromeDriver;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.boot.web.server.LocalServerPort;

import java.util.ArrayList;
import java.util.List;

@SpringBootTest(webEnvironment = SpringBootTest.WebEnvironment.RANDOM_PORT)
class CloudStorageApplicationTests {

	@LocalServerPort
	private int port;

	private WebDriver driver;

	private SignUpPage signUpPage;

	private LoginPage loginPage;

	private HomePage homePage;

	@BeforeAll
	static void beforeAll() {
		WebDriverManager.chromedriver().setup();
	}

	@BeforeEach
	public void beforeEach() {
		this.driver = new ChromeDriver();
		signUpPage = new SignUpPage(driver);
		loginPage = new LoginPage(driver);
		homePage = new HomePage(driver);
	}

	@AfterEach
	public void afterEach() {
		if (this.driver != null) {
			driver.quit();
		}
	}

	@Test
	public void getLoginPage() {
		driver.get("http://localhost:" + this.port + "/login");
		Assertions.assertEquals("Login", driver.getTitle());
	}

	@Test
	public void userAccess() {
		List<String> blockedEndpointsForUnauthorizedUsers = new ArrayList<>();
		blockedEndpointsForUnauthorizedUsers.add("/create/credential");
		blockedEndpointsForUnauthorizedUsers.add("/delete/note/{noteid}");
		blockedEndpointsForUnauthorizedUsers.add("/create/note");
		blockedEndpointsForUnauthorizedUsers.add("/delete/file/{fileId}");
		blockedEndpointsForUnauthorizedUsers.add("/view/{fileId}");
		for(String endpoint : blockedEndpointsForUnauthorizedUsers) {
			driver.get("http://localhost:" + this.port + endpoint);
			Assertions.assertEquals("Whitelabel Error Page",
					((ChromeDriver) driver).findElementByTagName("h1").getText());
		}
		driver.get("http://localhost:" + this.port + "/login");
		Assertions.assertEquals("Login", driver.getTitle());
		driver.get("http://localhost:" + this.port + "/signup");
		Assertions.assertEquals("Sign Up", driver.getTitle());
	}

	@Test
	public void signUp() {
		String username = "Berk";
		String password = "Yasar";
		driver.get("http://localhost:" + this.port + "/signup");
		signUpPage.signUpUser(username, password);
		driver.get("http://localhost:" + this.port + "/login");
		loginPage.loginUser(username, password);
		Assertions.assertEquals("Home", driver.getTitle());
		homePage.logOut();
		Assertions.assertNotEquals("Home", driver.getTitle());
	}
}
