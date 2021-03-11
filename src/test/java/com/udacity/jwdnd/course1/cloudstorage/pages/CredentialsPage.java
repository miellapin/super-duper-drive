package com.udacity.jwdnd.course1.cloudstorage.pages;

import org.openqa.selenium.By;
import org.openqa.selenium.JavascriptExecutor;
import org.openqa.selenium.WebDriver;
import org.openqa.selenium.WebElement;
import org.openqa.selenium.support.FindBy;
import org.openqa.selenium.support.PageFactory;

public class CredentialsPage {
    @FindBy(id = "nav-credentials-tab")
    private WebElement credentialsTab;

    @FindBy(id = "addNewCredentialButton")
    private WebElement addNewCredentialButton;

    @FindBy(id = "credential-url")
    private WebElement credentialUrl;

    @FindBy(id = "credential-username")
    private WebElement credentialUsername;

    @FindBy(id = "credential-password")
    private WebElement credentialPassword;

    @FindBy(id = "credentialSubmit")
    private WebElement credentialSubmitButton;

    @FindBy(className = "btn-danger")
    private WebElement deleteCredentialButton;

    @FindBy(className = "btn-success")
    private WebElement editCredentialButton;

    @FindBy(id = "saveCredentialChanges")
    private WebElement saveCredentialChanges;

    private final WebDriver webDriver;

    public CredentialsPage(WebDriver webDriver) {
        this.webDriver = webDriver;
        PageFactory.initElements(webDriver, this);
    }


    public void makeSureRightCredentialsTabIsActive() {
        ((JavascriptExecutor) webDriver).executeScript("arguments[0].click();", credentialsTab);
    }

    public void clickOnCredentialsEditButton() {
        ((JavascriptExecutor) webDriver).executeScript("arguments[0].click();", editCredentialButton);
    }

    public void changeModalCredentialsURL(String url) {
        ((JavascriptExecutor) webDriver).executeScript("arguments[0].click();", credentialUrl);
        ((JavascriptExecutor) webDriver).executeScript("arguments[0].value='" + url + "';",credentialUrl);
    }

    public void changeModalCredentialsUsername(String username) {
        ((JavascriptExecutor) webDriver).executeScript("arguments[0].click();", credentialUsername);
        ((JavascriptExecutor) webDriver).executeScript("arguments[0].value='" + username + "';",credentialUsername);
    }

    public void changeModalCredentialsPassword(String pwd) {
        ((JavascriptExecutor) webDriver).executeScript("arguments[0].click();", credentialPassword);
        ((JavascriptExecutor) webDriver).executeScript("arguments[0].value='" + pwd + "';",credentialPassword);
    }

    public void saveModalChangesButton() {
        ((JavascriptExecutor) webDriver).executeScript("arguments[0].click();", saveCredentialChanges);
    }

    public void clickOnAddNewCredentialsButton() {
        ((JavascriptExecutor) webDriver).executeScript("arguments[0].click();", addNewCredentialButton);
    }

    public void clickOnDeleteCredentialsButton() {
        ((JavascriptExecutor) webDriver).executeScript("arguments[0].click();", deleteCredentialButton);
    }

    public String getModalPasswordValue() {
        return webDriver.findElement(By.id("credential-password")).getAttribute("value");
    }
}
