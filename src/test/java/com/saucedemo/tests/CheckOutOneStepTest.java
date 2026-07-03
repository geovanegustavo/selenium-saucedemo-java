package com.saucedemo.tests;

import org.junit.jupiter.api.DisplayName;
import org.junit.jupiter.api.Test;

import com.saucedemo.base.BaseTest;
import com.saucedemo.base.TestData;
import com.saucedemo.pages.CartPage;
import com.saucedemo.pages.InventoryPage;
import com.saucedemo.pages.CheckoutStepOnePage;

public class CheckOutOneStepTest extends BaseTest {

    @Test
    @DisplayName("Deve exibir o produto correto dentro do carrinho após a adição")
    void deveRealizarCheckOutDeProduto() {
        // 1. Faz o login
        InventoryPage inventoryPage = loginPage.login(TestData.VALID_USER, TestData.PASSWORD);

        // 2. Adiciona o produto e guarda o nome dele para conferência
        inventoryPage.adicionarPrimeiroProdutoAoCarrinho();

        // 3. Navega para a página do carrinho (openCartPage agora retorna a CartPage)
        CartPage cartPage = inventoryPage.openCartPage();

        // 4. Clica no botão de checkout
        cartPage.clickCheckoutButton();

        // 5. 
        

    }

}
