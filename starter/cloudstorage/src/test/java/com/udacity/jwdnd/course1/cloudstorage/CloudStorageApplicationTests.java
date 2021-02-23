package com.udacity.jwdnd.course1.cloudstorage;

import io.github.bonigarcia.wdm.WebDriverManager;
import org.junit.jupiter.api.*;
import org.openqa.selenium.By;
import org.openqa.selenium.WebDriver;
import org.openqa.selenium.chrome.ChromeDriver;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.boot.web.server.LocalServerPort;

@SpringBootTest(webEnvironment = SpringBootTest.WebEnvironment.RANDOM_PORT)
class CloudStorageApplicationTests {

	@LocalServerPort
	private int port;

	private WebDriver driver;

	public String baseURL;

	@BeforeAll
	static void beforeAll() {
		WebDriverManager.chromedriver().setup();
//		this.driver = new ChromeDriver();
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

	@Test
	public void testUserSignupLoginPage() {
		String username = "pgupta";
		String password = "hexagon123";
		String fName = "Poonam";
		String lName = "Gupta";

		driver.get(this.baseURL + "/signup");

		SignupPage signupPage = new SignupPage(driver);
		signupPage.signup(fName, lName,username, password);

		driver.get(this.baseURL + "/login");

		LoginPage loginPage = new LoginPage(driver);
		loginPage.login(username, password);
		driver.get(this.baseURL + "/login?logout");
		Assertions.assertEquals("You have been logged out", driver.findElement(By.id("logout-msg")).getText());

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
	public void testRandomPage() {
		driver.get(this.baseURL + "/test");
		Assertions.assertEquals("Login", driver.getTitle());
	}

	public void createNote(NotesPage notesPage, String title, String description) {
		notesPage.addNote(title, description);
	}

	@Test
	public void createNoteTest() {
		testUserSignupLoginPage();
		NotesPage notesPage = new NotesPage(driver);
		createNote(notesPage, "Note title", "Note description");
		Assertions.assertEquals("You successfully added a new note", driver.findElement(By.id("success-msg")).getText());
		driver.get("http://localhost:" + this.port + "/home");
		notesPage.openNoteTabJS();
		Assertions.assertEquals("Note title",  driver.findElement(By.id("table-noteTitle")).getText());
	}

//	@Test
//	public void editNoteTest() {
//		testUserSignupLoginPage();
//		NotesPage notesPage = new NotesPage(driver);
//		createNote(notesPage, "Note title", "Note description");
//		Assertions.assertEquals("You successfully added a new note", driver.findElement(By.id("success-msg")).getText());
//		driver.get("http://localhost:" + this.port + "/home");
//		notesPage.openNoteTabJS();
//		notesPage.editNoteJS();
//		notePage.createNoteJS("noteTitle", "Edited note description");
//		notePage.saveNoteJS();
//		Assertions.assertEquals("Result", driver.getTitle());
//		Assertions.assertEquals("New note added !", resultPage.getSuccessMessage());
//		driver.get("http://localhost:" + this.port + "/home");
//		notePage.openNoteTabJS();
//		Assertions.assertEquals("noteTitle", notePage.getTableNoteTitle());
//	}

}
