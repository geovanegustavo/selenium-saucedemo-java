package com.saucedemo.pages;

import io.qameta.allure.Step;
import org.openqa.selenium.By;
import org.openqa.selenium.WebDriver;
import com.saucedemo.utils.EnvConfig;

public class LoginPage extends BasePage {

    private final By usernameInput = By.id("user-name");
    private final By passwordInput = By.id("password");
    private final By loginButton = By.id("login-button");
    private final By errorMessage = By.cssSelector("[data-test='error']");

    public LoginPage(WebDriver driver) {
        super(driver);
    }

    @Step("Abrir a pagina de login do SauceDemo")
    public LoginPage open() {
        driver.get(EnvConfig.baseUrl());
        return this;
    }

    @Step("Preencher usuario: {username}")
    public LoginPage typeUsername(String username) {
        type(usernameInput, username);
        return this;
    }

    @Step("Preencher senha")
    public LoginPage typePassword(String password) {
        type(passwordInput, password);
        return this;
    }

    @Step("Clicar no botao de login")
    public InventoryPage clickLoginButton() {
        click(loginButton);
        return new InventoryPage(driver);
    }

    @Step("Realizar login com usuario {username}")
    public InventoryPage login(String username, String password) {
        typeUsername(username);
        typePassword(password);
        return clickLoginButton();
    }

    @Step("Tentar login com usuario {username} (esperando falha)")
    public LoginPage loginExpectingFailure(String username, String password) {
        typeUsername(username);
        typePassword(password);
        click(loginButton);
        return this;
    }

    public String getErrorMessage() {
        return getText(errorMessage);
    }

    public boolean isErrorMessageDisplayed() {
        return isDisplayed(errorMessage);
    }

    public boolean isLoginButtonDisplayed() {
        return isDisplayed(loginButton);
    }
}