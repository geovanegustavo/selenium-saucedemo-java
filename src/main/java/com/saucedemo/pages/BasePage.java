package com.saucedemo.pages;

import org.openqa.selenium.By;
import org.openqa.selenium.WebDriver;
import org.openqa.selenium.WebElement;
import org.openqa.selenium.support.ui.ExpectedConditions;
import org.openqa.selenium.support.ui.WebDriverWait;
import com.saucedemo.utils.ElementHighlighter;

import java.time.Duration;

/**
 * Classe base para todos os Page Objects.
 * Centraliza o WebDriver, o WebDriverWait e acoes comuns,
 * evitando duplicacao de codigo entre as paginas.
 */
public abstract class BasePage {

    protected final WebDriver driver;
    protected final WebDriverWait wait;

    protected static final Duration DEFAULT_TIMEOUT = Duration.ofSeconds(10);

    // Guarda o ultimo locator consultado nesta thread, para que o AllureListener
    // consiga destacar o elemento certo mesmo quando a falha e uma asserção
    // do JUnit (fora do fluxo de clique/digitação do Selenium).
    private static final ThreadLocal<By> LAST_LOCATOR = new ThreadLocal<>();

    protected BasePage(WebDriver driver) {
        this.driver = driver;
        this.wait = new WebDriverWait(driver, DEFAULT_TIMEOUT);
    }

    public static By getLastLocator() {
        return LAST_LOCATOR.get();
    }

    public static void clearLastLocator() {
        LAST_LOCATOR.remove();
    }

    protected WebElement waitVisible(By locator) {
        LAST_LOCATOR.set(locator);
        return wait.until(ExpectedConditions.visibilityOfElementLocated(locator));
    }

    protected WebElement waitClickable(By locator) {
        LAST_LOCATOR.set(locator);
        return wait.until(ExpectedConditions.elementToBeClickable(locator));
    }

    protected void click(By locator) {
        try {
            waitClickable(locator).click();
        } catch (RuntimeException e) {
            ElementHighlighter.tryHighlight(driver, locator);
            throw e;
        }
    }

    protected void type(By locator, String text) {
        try {
            WebElement element = waitVisible(locator);
            element.clear();
            element.sendKeys(text);
        } catch (RuntimeException e) {
            ElementHighlighter.tryHighlight(driver, locator);
            throw e;
        }
    }

    protected String getText(By locator) {
        return waitVisible(locator).getText();
    }

    protected boolean isDisplayed(By locator) {
        try {
            return waitVisible(locator).isDisplayed();
        } catch (Exception e) {
            return false;
        }
    }

    public String getCurrentUrl() {
        return driver.getCurrentUrl();
    }

    public String getTitle() {
        return driver.getTitle();
    }
}