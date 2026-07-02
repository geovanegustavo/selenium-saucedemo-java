package com.saucedemo.tests;

import com.saucedemo.base.BaseTest;
import com.saucedemo.base.TestData;
import org.junit.jupiter.api.DisplayName;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.parallel.Execution;
import org.junit.jupiter.api.parallel.ExecutionMode;
import com.saucedemo.pages.InventoryPage;

import static org.junit.jupiter.api.Assertions.*;

@DisplayName("Testes de Inventário")
@Execution(ExecutionMode.CONCURRENT) // Permite que o JUnit execute estes testes em paralelo com total independência
class InventoryTest extends BaseTest {

    @Test
    @DisplayName("Deve adicionar produto ao carrinho")
    void deveAdicionarProdutoAoCarrinho() {
        // 1. Faz o login
        InventoryPage inventoryPage = loginPage.login(TestData.VALID_USER, TestData.PASSWORD);

        // 2. Adiciona o primeiro produto da lista no Carrinho
        inventoryPage.adicionarPrimeiroProdutoAoCarrinho();

        // 3. Valida se o produto foi inserido no carrinho e se o botão de adicionar produto mudou de nome (remover)
        assertAll("Verificações da página de Inventário pós adicionar produto ao carrinho",
            () -> assertTrue(inventoryPage.temItemNoCarrinho(), "O badge do carrinho deveria aparecer"),
            () -> assertEquals("1", inventoryPage.getQuantidadeItensCarrinho(), "A quantidade deveria ser igual a 1"),
            () -> assertTrue(inventoryPage.backpackRemoveButtonIsDisplayed(), "O botão remover produto deveria estar visível")
        );

        // 4. Faz o logout a partir da InventoryPage
        inventoryPage.logout();
    }

    @Test
    @DisplayName("Deve remover produto do carrinho")
    void deveRemoverProdutoDoCarrinho() {
        // 1. Faz o login
        InventoryPage inventoryPage = loginPage.login(TestData.VALID_USER, TestData.PASSWORD);

        // 2. Adiciona o primeiro produto da lista no Carrinho
        inventoryPage.adicionarPrimeiroProdutoAoCarrinho();

        // 3. Remove o primeiro produto da lista no Carrinho
        inventoryPage.removerPrimeiroProdutoDoCarrinho();

        // 4. Valida se o produto foi removido do carrinho e se o botão de remover produto mudou de nome (adicionar)
        assertAll("Verificações da página de Inventário pós remoção de produto ao carrinho",
            () -> assertEquals("0", inventoryPage.getQuantidadeItensCarrinho(), "A quantidade deveria ser igual a 0"),
            () -> assertTrue(inventoryPage.backpackAddButtonIsDisplayed(), "O botão adicionar produto deveria estar visível")
        );

        // 5. Faz o logout a partir da InventoryPage
        inventoryPage.logout();
    }

}