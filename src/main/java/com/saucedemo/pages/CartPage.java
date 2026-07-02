package com.saucedemo.pages;

import io.qameta.allure.Step;
import org.openqa.selenium.By;
import org.openqa.selenium.WebDriver;

public class CartPage extends BasePage {

    private final By pageTitle = By.className("title");
    private final By burgerMenuButton = By.id("react-burger-menu-btn");
    private final By logoutLink = By.id("logout_sidebar_link");
    private final By cartItemName = By.cssSelector("[data-test='inventory-item-name']");
    private final By cartItemPrice = By.cssSelector("[data-test='inventory-item-price']");
    private final By productRemoveButton = By.cssSelector("[data-test='remove-sauce-labs-backpack']");
    private final By continueShoppingButton = By.cssSelector("[data-test='continue-shopping']");
    private final By checkoutButton = By.cssSelector("[data-test='checkout']");

    public CartPage(WebDriver driver) {
        super(driver);
    }

    public String getPageTitle() {
        return getText(pageTitle);
    }

    public boolean isproductRemoveButtonDisplayed() {
        return isDisplayed(productRemoveButton);
    }

    public boolean isContinueShoppingButtonDisplayed() {
        return isDisplayed(continueShoppingButton);
    }

    public boolean isCheckoutButtonDisplayed() {
        return isDisplayed(checkoutButton);
    }

    @Step("Capturar o nome do produto exibido no carrinho")
    public String getCartItemName() {
        return getText(cartItemName);
    }

    @Step("Capturar o preço do produto exibido no carrinho")
    public Double getCartItemPrice() {
        String priceText = getText(cartItemPrice);
        if (priceText == null || priceText.isBlank()) {
        return 0.0;
    }
    // Remove o cifrão e converte para double
    return Double.parseDouble(priceText.replace("$", "").trim());
    }

    @Step("Abrir o menu lateral (burger menu)")
    public CartPage openBurgerMenu() {
        click(burgerMenuButton);
        waitVisible(logoutLink);
        return this;
    }

    @Step("Realizar logout")
    public void logout() {
        openBurgerMenu();
        click(logoutLink);
    }

}