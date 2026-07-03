package com.saucedemo.pages;

import io.qameta.allure.Step;
import org.openqa.selenium.By;
import org.openqa.selenium.WebDriver;

public class CheckoutStepOnePage extends BasePage {

    private final By pageTitle = By.className("title");
    private final By firstName = By.cssSelector("[data-test='firstName']");
    private final By lastName = By.cssSelector("[data-test='lastName']");
    private final By postalCode = By.cssSelector("[data-test='postalCode']");
    private final By cancel = By.cssSelector("[data-test='cancel']");
    private final By continueButton = By.cssSelector("[data-test='continue']");
    private final By errorButton = By.cssSelector("[data-test='error-button']");

    public CheckoutStepOnePage(WebDriver driver) {
        super(driver);
    }

    public String getPageTitle() {
        return getText(pageTitle);
    }

    public boolean isCancelButtonDisplayed() {
        return isDisplayed(cancel);
    }

    public boolean isContinueButtonDisplayed() {
        return isDisplayed(continueButton);
    }

    public boolean isErrorButtonDisplayed() {
        return isDisplayed(errorButton);
    }

    @Step("Preencher informações de checkout")
    public void fillCheckoutInformation(String firstName, String lastName, String postalCode) {
        type(this.firstName, firstName);
        type(this.lastName, lastName);
        type(this.postalCode, postalCode);
    }

}
