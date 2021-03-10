package com.udacity.jwdnd.course1.cloudstorage.pages;

import org.openqa.selenium.JavascriptExecutor;
import org.openqa.selenium.WebDriver;
import org.openqa.selenium.WebElement;
import org.openqa.selenium.support.FindBy;
import org.openqa.selenium.support.PageFactory;

public class NotesPage {
    @FindBy(id = "nav-notes-tab")
    private WebElement navNotesTab;

    @FindBy(id = "addNewNoteButton")
    private WebElement addNewNoteButton;

    @FindBy(id = "note-title")
    private WebElement noteTitleText;

    @FindBy(id = "note-description")
    private WebElement noteDescriptionText;

    @FindBy(id = "saveChangesButton")
    private WebElement saveChangesButton;

    @FindBy(className = "btn-danger")
    private WebElement deleteNoteButton;

    @FindBy(className = "btn-success")
    private WebElement editNoteButton;

    private final WebDriver webDriver;

    public NotesPage(WebDriver webDriver){
        this.webDriver = webDriver;
        PageFactory.initElements(webDriver, this);
    }

    public void openEmptyNoteModal() {
        ((JavascriptExecutor) webDriver).executeScript("arguments[0].click();", addNewNoteButton);
    }

    public void enterNewNote(String noteTitle, String noteDescription) {
        ((JavascriptExecutor) webDriver).executeScript("arguments[0].click();", noteTitleText);
        ((JavascriptExecutor) webDriver).executeScript("arguments[0].value='" + noteTitle + "';",noteTitleText);

        ((JavascriptExecutor) webDriver).executeScript("arguments[0].click();", noteDescriptionText);
        ((JavascriptExecutor) webDriver).executeScript("arguments[0].value='" + noteDescription + "';",noteDescriptionText);
    }

    public void submitModal() {
        ((JavascriptExecutor) webDriver).executeScript("arguments[0].click();", saveChangesButton);
    }

    public void makeSureYouAreOnNoteTab() {
        ((JavascriptExecutor) webDriver).executeScript("arguments[0].click();", navNotesTab);
    }

    public void clickOnDeleteNoteButton() {
        ((JavascriptExecutor) webDriver).executeScript("arguments[0].click();", deleteNoteButton);
    }

    public void clickOnEditNoteButton() {
        ((JavascriptExecutor) webDriver).executeScript("arguments[0].click();", editNoteButton);
    }

    public HomePage navigateNotes(WebDriver driver) {
        navNotesTab.click();
        return new HomePage(driver);
    }

    public void setNewTitle(String newTitle) {
        ((JavascriptExecutor) webDriver).executeScript("arguments[0].click();", noteTitleText);
        ((JavascriptExecutor) webDriver).executeScript("arguments[0].value='" + newTitle + "';",noteTitleText);
    }

}
