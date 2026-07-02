package com.saucedemo.utils;

import io.github.bonigarcia.wdm.WebDriverManager;
import org.openqa.selenium.WebDriver;
import org.openqa.selenium.chrome.ChromeDriver;
import org.openqa.selenium.chrome.ChromeOptions;
import org.openqa.selenium.edge.EdgeDriver;
import org.openqa.selenium.edge.EdgeOptions;
import org.openqa.selenium.firefox.FirefoxDriver;
import org.openqa.selenium.firefox.FirefoxOptions;

import java.util.HashMap;
import java.util.Map;

/**
 * Classe responsavel por criar e configurar instancias do WebDriver.
 * Suporta Chrome, Edge e Firefox. O navegador pode ser definido via
 * variavel de ambiente/propriedade do sistema "browser" (padrao: chrome).
 */
public class DriverFactory {

    private DriverFactory() {
        // classe utilitaria, nao deve ser instanciada
    }

    public static WebDriver createDriver() {
        // Silencia o console que o processo chromedriver.exe/msedgedriver.exe abre no Windows
        System.setProperty("webdriver.chrome.silentOutput", "true");
        System.setProperty("webdriver.edge.silentOutput", "true");

        String browser = System.getProperty("browser", System.getenv().getOrDefault("BROWSER", "chrome"));

        WebDriver driver;

        switch (browser.toLowerCase()) {
            case "firefox":
                WebDriverManager.firefoxdriver().setup();
                FirefoxOptions firefoxOptions = new FirefoxOptions();
                if (isHeadless()) {
                    firefoxOptions.addArguments("-headless");
                }
                driver = new FirefoxDriver(firefoxOptions);
                break;

            case "edge":
                WebDriverManager.edgedriver().setup();
                EdgeOptions edgeOptions = new EdgeOptions();

                Map<String, Object> edgePrefs = new HashMap<>();
                edgePrefs.put("credentials_enable_service", false);
                edgePrefs.put("profile.password_manager_enabled", false);
                edgePrefs.put("profile.password_manager_leak_detection", false);
                edgePrefs.put("profile.default_content_setting_values.notifications", 2);
                edgeOptions.setExperimentalOption("prefs", edgePrefs);
                edgeOptions.setExperimentalOption("excludeSwitches", new String[]{"enable-automation"});
                edgeOptions.setExperimentalOption("useAutomationExtension", false);

                if (isHeadless()) {
                    edgeOptions.addArguments("--headless=new");
                    edgeOptions.addArguments("--disable-gpu");
                    edgeOptions.addArguments("--window-size=1920,1080");
                    edgeOptions.addArguments("--window-position=-2400,-2400"); // joga a janela pra fora da tela visivel
                } else {
                    edgeOptions.addArguments("--start-maximized");
                }
                driver = new EdgeDriver(edgeOptions);
                break;

            case "chrome":
            default:
                WebDriverManager.chromedriver().setup();
                ChromeOptions chromeOptions = new ChromeOptions();
                chromeOptions.addArguments("--remote-allow-origins=*");

                chromeOptions.addArguments("--disable-features=PasswordLeakDetection,AutofillServerCommunication");
                chromeOptions.addArguments("--disable-save-password-bubble");

                Map<String, Object> chromePrefs = new HashMap<>();
                chromePrefs.put("credentials_enable_service", false);
                chromePrefs.put("profile.password_manager_enabled", false);
                chromePrefs.put("profile.password_manager_leak_detection", false);
                chromePrefs.put("profile.default_content_setting_values.notifications", 2);
                chromeOptions.setExperimentalOption("prefs", chromePrefs);
                chromeOptions.setExperimentalOption("excludeSwitches", new String[]{"enable-automation"});
                chromeOptions.setExperimentalOption("useAutomationExtension", false);

                if (isHeadless()) {
                    chromeOptions.addArguments("--headless=new");
                    chromeOptions.addArguments("--disable-gpu");
                    chromeOptions.addArguments("--window-size=1920,1080");
                    chromeOptions.addArguments("--window-position=-2400,-2400"); // joga a janela pra fora da tela visivel
                } else {
                    chromeOptions.addArguments("--start-maximized");
                }
                driver = new ChromeDriver(chromeOptions);
                break;
        }

        if (!isHeadless()) {
            driver.manage().window().maximize();
        }
        return driver;
    }

    private static boolean isHeadless() {
        return Boolean.parseBoolean(
                System.getProperty("headless", System.getenv().getOrDefault("HEADLESS", "false"))
        );
    }
}