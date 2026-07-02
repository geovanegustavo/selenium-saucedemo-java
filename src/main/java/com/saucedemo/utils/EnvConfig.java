package com.saucedemo.utils;

import io.github.cdimascio.dotenv.Dotenv;

/**
 * Classe responsável pela leitura das variaveis de configuracao do projeto
 * (URL do site, usuarios e senhas), carregadas a partir do arquivo .env
 * na raiz do projeto.
 *
 * Tambem aceita que as variaveis sejam sobrescritas por variaveis de
 * ambiente reais do sistema operacional (util para rodar em CI/CD,
 * onde normalmente nao existe um arquivo .env versionado).
 */
public final class EnvConfig {

    private static final Dotenv dotenv = Dotenv.configure()
            .ignoreIfMissing() // nao quebra caso o .env nao exista (ex: CI usando env vars do sistema)
            .load();

    private EnvConfig() {
        // classe utilitaria, nao deve ser instanciada
    }

    public static String get(String key) {
        String value = dotenv.get(key);
        if (value == null || value.isBlank()) {
            throw new IllegalStateException(
                    "Variavel '" + key + "' nao encontrada. Verifique o arquivo .env "
                            + "(veja .env.example) ou defina a variavel de ambiente correspondente.");
        }
        return value;
    }

    public static String get(String key, String defaultValue) {
        String value = dotenv.get(key);
        return (value == null || value.isBlank()) ? defaultValue : value;
    }

    public static String baseUrl() {
        return get("BASE_URL");
    }

    public static String standardUser() {
        return get("STANDARD_USER");
    }

    public static String standardPassword() {
        return get("STANDARD_PASSWORD");
    }

    public static String lockedOutUser() {
        return get("LOCKED_OUT_USER");
    }

    public static String problemUser() {
        return get("PROBLEM_USER");
    }

    public static String performanceGlitchUser() {
        return get("PERFORMANCE_GLITCH_USER");
    }

    public static String invalidPassword() {
        return get("INVALID_PASSWORD");
    }
}

