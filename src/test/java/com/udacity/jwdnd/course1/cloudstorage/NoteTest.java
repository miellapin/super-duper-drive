package com.udacity.jwdnd.course1.cloudstorage;

import com.udacity.jwdnd.course1.cloudstorage.entity.Notes;
import com.udacity.jwdnd.course1.cloudstorage.pages.LoginPage;
import com.udacity.jwdnd.course1.cloudstorage.pages.NotesPage;
import com.udacity.jwdnd.course1.cloudstorage.pages.SignUpPage;
import com.udacity.jwdnd.course1.cloudstorage.services.NoteService;
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
public class NoteTest {

    @LocalServerPort
    private int port;

    private static WebDriver webDriver;

    private static WebDriverWait webDriverWait;

    private SignUpPage signUpPage;

    private LoginPage loginPage;

    private NotesPage notesPage;

    @Autowired
    NoteService noteService;

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
        notesPage = new NotesPage(webDriver);
    }

    @AfterEach
    public void afterEach() {
        if (webDriver != null) {
            webDriver.quit();
        }
    }

    @Test
    public void addAndDeleteNoteTest() {
        List<Notes> noteList = noteService.getAllNotes();
        Notes note = noteList.stream().filter(n -> n.getNotetitle().
                equalsIgnoreCase("Crime and Punishment")).findFirst().orElse(null);
        Assertions.assertNull(note);

        String username = "Berk";
        String password = "Yasar";
        webDriver.get("http://localhost:" + this.port + "/signup");
        signUpPage.signUpUser(username, password);
        webDriver.get("http://localhost:" + this.port + "/login");
        loginPage.loginUser(username, password);

        WebElement inputUserName = webDriverWait.until(wd -> wd.findElement(By.id("nav-notes-tab")));
        Assertions.assertNotNull(inputUserName);

        notesPage.makeSureYouAreOnNoteTab();
        notesPage.openEmptyNoteModal();

        notesPage.enterNewNote("Crime and Punishment", "Book by Dostoyevski");
        notesPage.submitModal();

        noteList.clear();
        noteList = noteService.getAllNotes();
        Notes notes = noteList.stream().filter(n -> n.getNotetitle().
                equalsIgnoreCase("Crime and Punishment")).findFirst().orElse(null);

        Assertions.assertNotNull(notes);
        notesPage.makeSureYouAreOnNoteTab();

        noteList.clear();
        notesPage.clickOnDeleteNoteButton();
        Notes deletedNote = noteService.getAllNotes().stream().filter(dn -> dn.getNotetitle().
                equalsIgnoreCase("Crime and Punishment")).findFirst().orElse(null);
        Assertions.assertNull(deletedNote);
    }

    @Test
    public void editNoteTest() {
        String username = "Berk";
        String password = "Yasar";
        webDriver.get("http://localhost:" + this.port + "/signup");
        signUpPage.signUpUser(username, password);
        webDriver.get("http://localhost:" + this.port + "/login");
        loginPage.loginUser(username, password);

        notesPage.makeSureYouAreOnNoteTab();
        notesPage.openEmptyNoteModal();

        notesPage.enterNewNote("Crime and Punishment", "Book by Dostoyevski");
        notesPage.submitModal();
        notesPage.makeSureYouAreOnNoteTab();
        notesPage.clickOnEditNoteButton();
        notesPage.setNewTitle("Les Miserables");
        notesPage.submitModal();

        List<Notes> noteList = noteService.getAllNotes();
        Notes editedNote = noteList.stream().filter(n -> n.getNotetitle().
                equalsIgnoreCase("Les Miserables")).findFirst().orElse(null);
        Assertions.assertNotNull(editedNote);
    }
}
