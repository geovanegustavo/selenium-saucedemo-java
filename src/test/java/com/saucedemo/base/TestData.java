package com.saucedemo.base;

import com.saucedemo.utils.EnvConfig;

/**
 * Usuarios fixos disponibilizados pelo proprio saucedemo.com para testes.
 * Os valores sao lidos do arquivo .env (veja utils.EnvConfig), nao ficando
 * mais hardcoded no codigo-fonte.
 * Referencia: https://www.saucedemo.com/
 */
public final class TestData {

    public static final String VALID_USER = EnvConfig.standardUser();
    public static final String LOCKED_OUT_USER = EnvConfig.lockedOutUser();
    public static final String PROBLEM_USER = EnvConfig.problemUser();
    public static final String PERFORMANCE_GLITCH_USER = EnvConfig.performanceGlitchUser();

    public static final String PASSWORD = EnvConfig.standardPassword();
    public static final String INVALID_PASSWORD = EnvConfig.invalidPassword();

    private TestData() {
        // classe utilitaria de constantes
    }
}

