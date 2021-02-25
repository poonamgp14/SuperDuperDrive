package com.udacity.jwdnd.course1.cloudstorage;

import org.openqa.selenium.JavascriptExecutor;
import org.openqa.selenium.WebDriver;
import org.openqa.selenium.WebElement;
import org.openqa.selenium.support.FindBy;
import org.openqa.selenium.support.PageFactory;

public class LoginPage {

    @FindBy(css="#inputUsername")
    private WebElement usernameField;

    @FindBy(css="#inputPassword")
    private WebElement passwordField;

    private final WebDriver driver;

    @FindBy(css="#login-submit-button")
    private WebElement submitButton;

    public LoginPage(WebDriver webDriver) {
        this.driver = webDriver;
        PageFactory.initElements(webDriver, this);
    }

    public void login(String username, String password) {
        System.out.println(username);
        System.out.println(password);
        ((JavascriptExecutor) driver).executeScript("arguments[0].value='" + username + "';", usernameField);
        ((JavascriptExecutor) driver).executeScript("arguments[0].value='" + password + "';", passwordField);
        ((JavascriptExecutor) driver).executeScript("arguments[0].click();", submitButton);
    }

}

