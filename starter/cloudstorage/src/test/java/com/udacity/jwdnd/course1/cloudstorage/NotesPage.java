package com.udacity.jwdnd.course1.cloudstorage;


import org.openqa.selenium.By;
import org.openqa.selenium.JavascriptExecutor;
import org.openqa.selenium.WebDriver;
import org.openqa.selenium.WebElement;
import org.openqa.selenium.support.FindBy;
import org.openqa.selenium.support.PageFactory;
import org.openqa.selenium.support.ui.WebDriverWait;

import java.util.List;

public class NotesPage {
	@FindBy(css = "#nav-notes-tab")
	private WebElement notesTabField;

//	@FindBy(css = "#notes-table-body")
//	private WebElement notesTable;

	@FindBy(css = "#userTable")
	private WebElement notesTable;

	@FindBy (css="#add-note-button")
	private WebElement addButton;

	@FindBy(css = "#note-id")
	private WebElement noteId;

	@FindBy(css = "#note-title")
	private WebElement noteTitle;

	@FindBy(css="#note-description")
	private WebElement noteDescription;

	@FindBy(css="#noteSubmit")
	private WebElement noteSubmit;

	@FindBy (css="#edit-note-button")
	private WebElement editButton;

	@FindBy (css="#delete-note-button")
	private WebElement deleteButton;

	@FindBy(css = "#edit-note-title")
	private WebElement editTitle;

	@FindBy(css="#edit-note-description")
	private WebElement editDescription;

	@FindBy(css="#editNoteSubmit")
	private WebElement editSubmit;


	@FindBy(css="#deleteNoteSubmit")
	private WebElement deleteSubmit;

	private final WebDriver driver;

	private final WebDriverWait wait;

	public NotesPage(WebDriver webDriver) {
		this.driver = webDriver;
		PageFactory.initElements(webDriver, this);
		wait = new WebDriverWait(driver, 1000);
	}

	public void openNoteTabJS() {
		((JavascriptExecutor) driver).executeScript("arguments[0].click();", notesTabField);
	}

	public void  addNote(String title, String description){
		this.openNoteTabJS();
		((JavascriptExecutor) driver).executeScript("arguments[0].click();", addButton);
		((JavascriptExecutor) driver).executeScript("arguments[0].value='" + title + "';", noteTitle);
		((JavascriptExecutor) driver).executeScript("arguments[0].value='" + description + "';", noteDescription);
		((JavascriptExecutor) driver).executeScript("arguments[0].click();", noteSubmit);
	}

	public void editNote(String title, String desc){
		((JavascriptExecutor) driver).executeScript("arguments[0].click();", editButton);
		((JavascriptExecutor) driver).executeScript("arguments[0].value='" + title + "';", editTitle);
		((JavascriptExecutor) driver).executeScript("arguments[0].value='" + desc + "';", editDescription);
		((JavascriptExecutor) driver).executeScript("arguments[0].click();", editSubmit);
	}

	public void deleteNote(){
		((JavascriptExecutor) driver).executeScript("arguments[0].click();", deleteButton);
		((JavascriptExecutor) driver).executeScript("arguments[0].click();", deleteSubmit);

	}

	public boolean hasNotes() {
		List<WebElement> notesList = notesTable.findElements(By.id("table-noteTitle"));
		return notesList.size() != 0;
	}
}
