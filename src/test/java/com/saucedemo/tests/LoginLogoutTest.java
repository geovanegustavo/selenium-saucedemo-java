package com.saucedemo.tests;

import com.saucedemo.base.BaseTest;
import com.saucedemo.base.TestData;
import org.junit.jupiter.api.DisplayName;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.parallel.Execution;
import org.junit.jupiter.api.parallel.ExecutionMode;
import com.saucedemo.pages.InventoryPage;
import com.saucedemo.pages.LoginPage;

import static org.junit.jupiter.api.Assertions.*;

@DisplayName("Testes de Autenticação")
@Execution(ExecutionMode.CONCURRENT) // Permite que o JUnit execute estes testes em paralelo com total independência
class LoginLogoutTest extends BaseTest {

    @Test
    @DisplayName("Deve realizar login com sucesso usando credenciais validas")
    void deveRealizarLoginComSucesso() {
        InventoryPage inventoryPage = loginPage.login(TestData.VALID_USER, TestData.PASSWORD);

        assertAll("Verificações da página de Inventário pós-login",
            () -> assertTrue(inventoryPage.isDisplayed(), "A pagina de produtos deveria estar visivel"),
            () -> assertEquals("Products", inventoryPage.getPageTitle(), "O titulo da pagina incorreto"),
            () -> assertTrue(inventoryPage.getCurrentUrl().contains("inventory.html"), "A URL não mudou para o inventário")
        );
    }

    @Test
    @DisplayName("Deve realizar logout com sucesso apos login valido")
    void deveRealizarLogoutComSucesso() {
        // Cada teste cria seu próprio estado do início ao fim
        InventoryPage inventoryPage = loginPage.login(TestData.VALID_USER, TestData.PASSWORD);
        LoginPage retornarParaLogin = inventoryPage.logout();

        // Garante que o logout limpou a sessão e voltou para a tela inicial
        assertAll("Verificações pós-logout",
            () -> assertTrue(retornarParaLogin.getCurrentUrl().equals("https://www.saucedemo.com/") 
                    || retornarParaLogin.getCurrentUrl().contains("index.html"), "Não retornou para a página inicial"),
            () -> assertFalse(retornarParaLogin.isErrorMessageDisplayed(), "Não deveria exibir erro ao deslogar")
        );
    }

    @Test
    @DisplayName("Nao deve permitir login com senha invalida")
    void naoDevePermitirLoginComSenhaInvalida() {
        LoginPage resultPage = loginPage.loginExpectingFailure(TestData.VALID_USER, TestData.INVALID_PASSWORD);

        assertAll("Validação de erro - Senha Inválida",
            () -> assertTrue(resultPage.isErrorMessageDisplayed(), "Mensagem de erro deveria ser exibida"),
            () -> assertTrue(resultPage.getErrorMessage().contains("Username and password do not match"),
                "A mensagem de erro deveria indicar usuario/senha invalidos")
        );
    }

    @Test
    @DisplayName("Nao deve permitir login com usuario bloqueado (locked_out_user)")
    void naoDevePermitirLoginComUsuarioBloqueado() {
        LoginPage resultPage = loginPage.loginExpectingFailure(TestData.LOCKED_OUT_USER, TestData.PASSWORD);

        assertAll("Validação de erro - Usuário Bloqueado",
            () -> assertTrue(resultPage.isErrorMessageDisplayed(), "Mensagem de erro deveria ser exibida"),
            () -> assertTrue(resultPage.getErrorMessage().contains("locked out"),
                "A mensagem de erro deveria indicar que o usuario esta bloqueado")
        );
    }

    @Test
    @DisplayName("Nao deve permitir login com campos vazios")
    void naoDevePermitirLoginComCamposVazios() {
        LoginPage resultPage = loginPage.loginExpectingFailure("", "");

        assertAll("Validação de erro - Campos Vazios",
            () -> assertTrue(resultPage.isErrorMessageDisplayed(), "Mensagem de erro deveria ser exibida"),
            () -> assertTrue(resultPage.getErrorMessage().contains("Username is required"),
                "A mensagem de erro deveria indicar que o usuario e obrigatorio")
        );
    }
}