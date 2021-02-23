package com.udacity.jwdnd.course1.cloudstorage;


import org.openqa.selenium.WebDriver;
import org.openqa.selenium.WebElement;
import org.openqa.selenium.support.FindBy;
import org.openqa.selenium.support.PageFactory;

public class NotesPage {
	@FindBy(css = "#nav-notes-tab")
	private WebElement notesTabField;

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

	@FindBy (css="#add-note-button")
	private WebElement editButton;

	@FindBy(css = "#edit-note-id")
	private WebElement editNoteId;

	@FindBy(css = "#edit-note-title")
	private WebElement editTitle;

	@FindBy(css="#edit-note-description")
	private WebElement editDescription;

	@FindBy(css="#editNoteSubmit")
	private WebElement editSubmit;

	@FindBy(css = "#delete-note-id")
	private WebElement deleteNoteId;

	@FindBy(css="#deleteNoteSubmit")
	private WebElement deleteSubmit;

	public NotesPage(WebDriver webDriver) {
		PageFactory.initElements(webDriver, this);
	}

	public void openNoteTabJS() {
		this.notesTabField.click();
	}

	public void  addNote(String title, String description){
		this.openNoteTabJS();
		this.addButton.click();
		this.noteTitle.sendKeys(title);
		this.noteDescription.sendKeys(description);
		this.noteSubmit.click();
	}

	public void editNote(String title, String desc){
		this.editButton.click();
		this.editTitle.sendKeys(title);
		this.editDescription.sendKeys(desc);
		this.editSubmit.click();
	}

	public void deleteNote(){
		this.deleteSubmit.click();

	}
}
