package com.saucedemo.utils;

import org.openqa.selenium.By;
import org.openqa.selenium.JavascriptExecutor;
import org.openqa.selenium.WebDriver;
import org.openqa.selenium.WebElement;

import java.util.List;

/**
 * Utilitario responsavel por destacar visualmente (borda vermelha + fundo
 * amarelo) um elemento na tela, usado como evidencia em screenshots de falha.
 * Melhor esforco: se o elemento nao existir mais no DOM, apenas ignora.
 */
public final class ElementHighlighter {

    private ElementHighlighter() {
    }

    public static void highlight(WebDriver driver, WebElement element) {
        try {
            ((JavascriptExecutor) driver).executeScript(
                    "arguments[0].style.border='4px solid red';"
                            + "arguments[0].style.backgroundColor='yellow';"
                            + "arguments[0].scrollIntoView({block: 'center'});",
                    element
            );
        } catch (Exception ignored) {
            // Nao deixa uma falha no highlight mascarar a excecao original do teste
        }
    }

    public static void tryHighlight(WebDriver driver, By locator) {
        if (driver == null || locator == null) {
            return;
        }
        try {
            List<WebElement> elements = driver.findElements(locator);
            if (!elements.isEmpty()) {
                highlight(driver, elements.get(0));
            }
        } catch (Exception ignored) {
            // Elemento pode nao existir mais na pagina atual; ignora silenciosamente
        }
    }
}
