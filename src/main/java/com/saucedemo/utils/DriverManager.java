package com.saucedemo.utils;

import org.openqa.selenium.WebDriver;

/**
 * Guarda a instancia do WebDriver da thread atual, permitindo que
 * componentes fora do fluxo normal (como o AllureListener) consigam
 * acessar o driver ativo no momento de uma falha.
 */
public final class DriverManager {

    private static final ThreadLocal<WebDriver> DRIVER_THREAD_LOCAL = new ThreadLocal<>();

    private DriverManager() {
    }

    public static void setDriver(WebDriver driver) {
        DRIVER_THREAD_LOCAL.set(driver);
    }

    public static WebDriver getDriver() {
        return DRIVER_THREAD_LOCAL.get();
    }

    public static void removeDriver() {
        DRIVER_THREAD_LOCAL.remove();
    }
}
