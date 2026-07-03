package com.saucedemo.tests;

import com.saucedemo.base.BaseTest;
import com.saucedemo.base.TestData;
import org.junit.jupiter.api.DisplayName;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.parallel.Execution;
import org.junit.jupiter.api.parallel.ExecutionMode;
import com.saucedemo.pages.InventoryPage;
import com.saucedemo.pages.CartPage;

import static org.junit.jupiter.api.Assertions.*;

@DisplayName("Testes do Carrinho de Compras")
@Execution(ExecutionMode.CONCURRENT)
class CartTest extends BaseTest {

    @Test
    @DisplayName("Deve exibir o produto correto dentro do carrinho após a adição")
    void deveAdicionarProdutoAoCarrinhoEVerificarNoCarrinho() {
        // 1. Faz o login
        InventoryPage inventoryPage = loginPage.login(TestData.VALID_USER, TestData.PASSWORD);

        // 2. Adiciona o produto e guarda o nome dele para conferência
        inventoryPage.adicionarPrimeiroProdutoAoCarrinho();
        String productNameExpected = inventoryPage.getProductName();
        Double productPriceExpected = inventoryPage.getProductPrice();

        // 3. Navega para a página do carrinho (openCartPage agora retorna a CartPage)
        CartPage cartPage = inventoryPage.openCartPage();

        // 4. Valida se a página mudou e se o produto que está lá dentro é o mesmo
        assertAll("Verificações da página de Carrinho pós adição",
            () -> assertEquals("Your Cart", cartPage.getPageTitle(), "O título da página deveria ser 'Your Cart'"),
            () -> assertEquals(productNameExpected, cartPage.getCartItemName(), "O produto exibido no carrinho não é o que foi adicionado"),
            () -> assertEquals(productPriceExpected, cartPage.getCartItemPrice(), "O preço do produto exibido no carrinho não é o que foi adicionado"),
            () -> assertTrue(cartPage.isProductRemoveButtonDisplayed(), "O botão para remover produto do carrinho deveria aparecer"),
            () -> assertTrue(cartPage.isContinueShoppingButtonDisplayed(), "O botão para retornar ao inventário deveria aparecer"),
            () -> assertTrue(cartPage.isCheckoutButtonDisplayed(), "O botão para confirmar o checkout deveria aparecer")
        );

        // 5. Faz o logout a partir da CartPage
        cartPage.logout();
    }
}