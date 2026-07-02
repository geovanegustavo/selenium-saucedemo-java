package com.saucedemo.pages;

import io.qameta.allure.Step;
import org.openqa.selenium.By;
import org.openqa.selenium.WebDriver;

public class InventoryPage extends BasePage {

    private final By pageTitle = By.className("title");
    private final By burgerMenuButton = By.id("react-burger-menu-btn");
    private final By logoutLink = By.id("logout_sidebar_link");
    private final By inventoryContainer = By.id("inventory_container");
    private final By shoppingCartLink = By.className("shopping_cart_link");

    private final By backpackAddButton = By.cssSelector("[data-test='add-to-cart-sauce-labs-backpack']");
    private final By backpackRemoveButton = By.cssSelector("[data-test='remove-sauce-labs-backpack']");
    private final By shoppingCartBadge = By.className("shopping_cart_badge");

    private final By productName = By.cssSelector("[data-test='inventory-item-name']");
    private final By productPrice = By.cssSelector("[data-test='inventory-item-price']");
    

    public InventoryPage(WebDriver driver) {
        super(driver);
    }

    public boolean isDisplayed() {
        return isDisplayed(inventoryContainer);
    }

    public String getProductName() {
        return getText(productName);
    }

    @Step("Capturar o preço do produto exibido no carrinho")
    public Double getProductPrice() {
        String priceText = getText(productPrice);
        if (priceText == null || priceText.isBlank()) {
        return 0.0;
    }
    // Remove o cifrão e converte para double
    return Double.parseDouble(priceText.replace("$", "").trim());
    }

    public String getPageTitle() {
        return getText(pageTitle);
    }

    @Step("Abrir o menu lateral (burger menu)")
    public InventoryPage openBurgerMenu() {
        click(burgerMenuButton);
        waitVisible(logoutLink);
        return this;
    }

    @Step("Realizar logout")
    public LoginPage logout() {
        openBurgerMenu();
        click(logoutLink);
        return new LoginPage(driver);
    }

    public boolean isShoppingCartDisplayed() {
        return isDisplayed(shoppingCartLink);
    }

    @Step("Adicionar a mochila ao carrinho")
    public InventoryPage adicionarPrimeiroProdutoAoCarrinho() {
        click(backpackAddButton);
        return this;
    }

    public boolean backpackRemoveButtonIsDisplayed() {
        return isDisplayed(backpackRemoveButton);
    }

    @Step("Remover a mochila do carrinho através da página de inventário")
    public InventoryPage removerPrimeiroProdutoDoCarrinho() {
        click(backpackRemoveButton);
        return this;
    }

    public boolean backpackAddButtonIsDisplayed() {
        return isDisplayed(backpackAddButton);
    }

    public boolean temItemNoCarrinho() {
        return isDisplayed(shoppingCartBadge);
    }

    public String getQuantidadeItensCarrinho() {
        if (temItemNoCarrinho()) {
            return getText(shoppingCartBadge);
        }
        return "0";
    }

    @Step("Abrir a página do carrinho")
    public CartPage openCartPage() {
        click(shoppingCartBadge);
        return new CartPage(driver);
    }

}