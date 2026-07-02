package com.saucedemo.base;

import io.qameta.allure.Allure;
import org.junit.jupiter.api.extension.AfterTestExecutionCallback;
import org.junit.jupiter.api.extension.ExtensionContext;
import org.openqa.selenium.OutputType;
import org.openqa.selenium.TakesScreenshot;
import org.openqa.selenium.WebDriver;
import com.saucedemo.pages.BasePage;
import com.saucedemo.utils.DriverManager;
import com.saucedemo.utils.ElementHighlighter;

import java.io.ByteArrayInputStream;
import java.io.IOException;
import java.nio.file.Files;
import java.nio.file.Path;
import java.nio.file.Paths;
import java.time.LocalDateTime;
import java.time.format.DateTimeFormatter;
import java.util.Comparator;

/**
 * Extensao JUnit 5 que, quando um teste falha:
 * 1. Destaca (highlight) o ultimo elemento consultado na tela
 * 2. Anexa o screenshot ao relatorio Allure
 * 3. Salva o screenshot tambem em target/screenshots, para consulta rapida
 *    sem precisar abrir o relatorio Allure
 *
 * A pasta target/screenshots e limpa automaticamente no inicio de cada
 * execucao (bloco estatico, roda uma unica vez por "mvn test"), garantindo
 * que prints de execucoes antigas nao se acumulem.
 */
public class AllureListener implements AfterTestExecutionCallback {

    private static final Path SCREENSHOTS_DIR = Paths.get("target", "screenshots");
    private static final DateTimeFormatter TIMESTAMP_FORMAT = DateTimeFormatter.ofPattern("yyyyMMdd_HHmmss");

    static {
        clearScreenshotsDirectory();
    }

    private static void clearScreenshotsDirectory() {
        try {
            if (Files.exists(SCREENSHOTS_DIR)) {
                try (var paths = Files.walk(SCREENSHOTS_DIR)) {
                    paths.sorted(Comparator.reverseOrder())
                            .forEach(path -> {
                                try {
                                    Files.deleteIfExists(path);
                                } catch (IOException ignored) {
                                    // melhor esforco: nao interrompe a execucao dos testes
                                }
                            });
                }
            }
            Files.createDirectories(SCREENSHOTS_DIR);
        } catch (IOException e) {
            System.err.println("Nao foi possivel preparar a pasta de screenshots: " + e.getMessage());
        }
    }

    @Override
    public void afterTestExecution(ExtensionContext context) {
        if (context.getExecutionException().isPresent()) {
            WebDriver driver = DriverManager.getDriver();
            if (driver instanceof TakesScreenshot) {
                ElementHighlighter.tryHighlight(driver, BasePage.getLastLocator());

                byte[] screenshot = ((TakesScreenshot) driver).getScreenshotAs(OutputType.BYTES);

                Allure.addAttachment(
                        "Screenshot da falha - " + context.getDisplayName(),
                        new ByteArrayInputStream(screenshot)
                );

                saveScreenshotToDisk(screenshot, context.getDisplayName());
            }
        }
        BasePage.clearLastLocator();
    }

    private void saveScreenshotToDisk(byte[] screenshot, String testName) {
        try {
            String timestamp = LocalDateTime.now().format(TIMESTAMP_FORMAT);
            String safeName = testName.replaceAll("[^a-zA-Z0-9-_]", "_");
            Path filePath = SCREENSHOTS_DIR.resolve(safeName + "_" + timestamp + ".png");
            Files.write(filePath, screenshot);
            System.out.println("Screenshot salvo em: " + filePath.toAbsolutePath());
        } catch (IOException e) {
            System.err.println("Nao foi possivel salvar o screenshot em disco: " + e.getMessage());
        }
    }
}