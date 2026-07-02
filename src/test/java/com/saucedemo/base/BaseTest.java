package com.saucedemo.base;

import org.junit.jupiter.api.AfterEach;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.extension.ExtendWith;
import org.openqa.selenium.WebDriver;
import com.saucedemo.pages.LoginPage;
import com.saucedemo.utils.DriverFactory;
import com.saucedemo.utils.DriverManager;

/**
 * Classe base para as classes de teste.
 * Responsavel por inicializar e encerrar o WebDriver a cada teste,
 * garantindo isolamento entre os cenarios, e por registrar o AllureListener
 * (herdado automaticamente por todas as subclasses).
 */
@ExtendWith(AllureListener.class)
public abstract class BaseTest {

    protected WebDriver driver;
    protected LoginPage loginPage;

    @BeforeEach
    void setUp() {
        driver = DriverFactory.createDriver();
        DriverManager.setDriver(driver);
        loginPage = new LoginPage(driver).open();
    }

    @AfterEach
    void tearDown() {
        if (driver != null) {
            driver.quit();
        }
        DriverManager.removeDriver();
    }
}