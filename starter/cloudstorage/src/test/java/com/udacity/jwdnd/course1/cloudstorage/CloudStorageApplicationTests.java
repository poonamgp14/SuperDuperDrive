package com.udacity.jwdnd.course1.cloudstorage;

import io.github.bonigarcia.wdm.WebDriverManager;
import org.junit.jupiter.api.*;
import org.openqa.selenium.By;
import org.openqa.selenium.JavascriptExecutor;
import org.openqa.selenium.WebDriver;
import org.openqa.selenium.chrome.ChromeDriver;
import org.openqa.selenium.support.ui.ExpectedConditions;
import org.openqa.selenium.support.ui.WebDriverWait;
import org.springframework.boot.test.autoconfigure.jdbc.AutoConfigureTestDatabase;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.boot.web.server.LocalServerPort;
import org.springframework.test.annotation.DirtiesContext;
import org.springframework.test.context.ActiveProfiles;

@SpringBootTest(webEnvironment = SpringBootTest.WebEnvironment.RANDOM_PORT)
@ActiveProfiles("test")
@DirtiesContext(classMode = DirtiesContext.ClassMode.BEFORE_EACH_TEST_METHOD)
//@AutoConfigureTestDatabase(replace = AutoConfigureTestDatabase.Replace.ANY)
class CloudStorageApplicationTests {

	@LocalServerPort
	private int port;

	private WebDriver driver;

	public String baseURL;

	public String username = "pgupta";
	public String password = "hexagon123";

	@BeforeAll
	static void beforeAll() {
		WebDriverManager.chromedriver().setup();
	}

	@BeforeEach
	public void beforeEach() {
		this.baseURL = "http://localhost:" + this.port;
		System.setProperty("webdriver.chrome.driver", "/usr/lib/chromium-browser/chromedriver");
		this.driver = new ChromeDriver();
	}

	@AfterEach
	public void afterEach() {
		if (this.driver != null) {
			driver.quit();
		}
	}

	// Perform signup process
	public void signup() {
		String fName = "Poonam";
		String lName = "Gupta";
		driver.get(this.baseURL + "/signup");
		SignupPage signupPage = new SignupPage(driver);
		signupPage.signup(fName, lName,this.username, this.password);
	}

	//Perform login process
	public void login(){
		driver.get(this.baseURL + "/login");
		LoginPage loginPage = new LoginPage(driver);
		loginPage.login(this.username, this.password);

	}


	@Test
	public void getSignupPage() {
		driver.get(this.baseURL + "/signup");
		Assertions.assertEquals("Sign Up", driver.getTitle());
	}

	@Test
	public void getLoginPage() {
		driver.get(this.baseURL + "/login");
		Assertions.assertEquals("Login", driver.getTitle());
	}


	@Test
	public void getHomePage() {
		driver.get(this.baseURL + "/home");
		Assertions.assertEquals("Login", driver.getTitle());
	}

	@Test
	public void testUserSignupLoginPage() {
		signup();
		login();
//		new WebDriverWait(driver, 60).until(ExpectedConditions.titleIs("Home"));
//		((JavascriptExecutor) driver).executeScript("arguments[0].click();", driver.findElement(By.id("logoutButton")));
//		new WebDriverWait(driver, 60).until(ExpectedConditions.titleIs("Login"));
		WebDriverWait wait = new WebDriverWait(driver, 100);
		driver.get(this.baseURL + "/login?logout");
		Assertions.assertEquals("You have been logged out", driver.findElement(By.id("logout-msg")).getText());

	}

	@Test
	public void testRandomPage() {
		driver.get(this.baseURL + "/test");
		Assertions.assertEquals("Login", driver.getTitle());
	}

	public void createNote(NotesPage notesPage, String title, String description) {
		notesPage.addNote(title, description);
	}

	@Test
	public void createNoteTest() {
		signup();
		login();
		NotesPage notesPage = new NotesPage(driver);
		createNote(notesPage, "Note title", "Note description");
		Assertions.assertEquals("You successfully added a new note", driver.findElement(By.id("success-msg")).getText());
		driver.get(this.baseURL + "/home");
		notesPage.openNoteTabJS();
		Assertions.assertEquals("Note title",  driver.findElement(By.id("table-noteTitle")).getAttribute("innerHTML"));
	}

	@Test
	public void editNoteTest() {
		signup();
		login();
		NotesPage notesPage = new NotesPage(driver);
		createNote(notesPage, "Note title", "Note description");
		Assertions.assertEquals("You successfully added a new note", driver.findElement(By.id("success-msg")).getText());
		driver.get("http://localhost:" + this.port + "/home");
		notesPage.openNoteTabJS();
		notesPage.editNote("note Title edited", "Edited note description");
		driver.get("http://localhost:" + this.port + "/home");
		notesPage.openNoteTabJS();
		Assertions.assertEquals("note Title edited",  driver.findElement(By.id("table-noteTitle")).getAttribute("innerHTML"));
	}

	@Test
	public void deleteNoteTest() {
		signup();
		login();
		NotesPage notesPage = new NotesPage(driver);
		createNote(notesPage, "Note title", "Note description");
		Assertions.assertEquals("You successfully added a new note", driver.findElement(By.id("success-msg")).getText());
		driver.get("http://localhost:" + this.port + "/home");
		notesPage.openNoteTabJS();
		notesPage.deleteNote();
		driver.get("http://localhost:" + this.port + "/home");
		notesPage.openNoteTabJS();
		Assertions.assertEquals(false, notesPage.hasNotes());
	}

	public void createCredential(CredentialsPage credentialsPage, String url, String username ,String password) {
		credentialsPage.addCredential(url, username, password);
	}

	@Test
	public void createCredentialTest() {
		signup();
		login();
		CredentialsPage credentialsPage = new CredentialsPage(driver);
		createCredential(credentialsPage, "httpabc", "pgupta", "12345");
		Assertions.assertEquals("You successfully added a new credential", driver.findElement(By.id("success-msg")).getText());
		driver.get(this.baseURL + "/home");
		credentialsPage.openCredentialTabJS();
		Assertions.assertEquals("httpabc",  driver.findElement(By.id("table-credentialUrl")).getAttribute("innerHTML"));
	}

	@Test
	public void editcredentialTest() {
		signup();
		login();
		CredentialsPage credentialsPage = new CredentialsPage(driver);
		createCredential(credentialsPage, "httpabc", "pgupta", "12345");
		Assertions.assertEquals("You successfully added a new credential", driver.findElement(By.id("success-msg")).getText());
		driver.get("http://localhost:" + this.port + "/home");
		credentialsPage.openCredentialTabJS();
		credentialsPage.editCredential("edited url", "Edited username", "Edited password");
		driver.get("http://localhost:" + this.port + "/home");
		credentialsPage.openCredentialTabJS();
		Assertions.assertEquals("edited url",  driver.findElement(By.id("table-credentialUrl")).getAttribute("innerHTML"));
	}

	@Test
	public void deleteCredentialTest() {
		signup();
		login();
		CredentialsPage credentialsPage = new CredentialsPage(driver);
		createCredential(credentialsPage, "httpabc", "pgupta", "12345");
		Assertions.assertEquals("You successfully added a new credential", driver.findElement(By.id("success-msg")).getText());
		driver.get("http://localhost:" + this.port + "/home");
		credentialsPage.openCredentialTabJS();
		credentialsPage.deleteCredential();
		driver.get("http://localhost:" + this.port + "/home");
		credentialsPage.openCredentialTabJS();
		Assertions.assertEquals(false, credentialsPage.hasCredentials());
	}

}
