package org.example.onlineshoptest.tests;

import io.qameta.allure.Description;
import io.qameta.allure.Epic;
import io.qameta.allure.Severity;
import io.qameta.allure.SeverityLevel;
import io.qameta.allure.Story;
import org.example.onlineshoptest.baseTests.BaseUITest;
import org.example.onlineshoptest.baseTests.ScreenshotExtension;
import org.example.onlineshoptest.pages.HomePage;
import org.example.onlineshoptest.pages.SearchPage;
import org.example.onlineshoptest.pages.ShoppingCartPage;
import org.junit.jupiter.api.DisplayName;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;

import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.junit.jupiter.api.Assertions.assertTrue;

@Epic("UI Tests")
@ExtendWith(ScreenshotExtension.class)
public class TestExample extends BaseUITest {

    @Test
    @Story("Поиск товара")
    @DisplayName("Поиск холодильников")
    @Description("Проверяем поиск по ключевому слову 'Холодильник' на сайте 5element.by")
    @Severity(SeverityLevel.NORMAL)
    void shouldSearch() {
        HomePage homePage = new HomePage(page);

        homePage.open();
        homePage.search("Холодильник");

        assertTrue(page.locator("h1").textContent().contains("Холодильник"));
    }

    @Test
    @Story("Навигация по каталогу")
    @DisplayName("Переход к категории")
    @Description("Проверяем переход в категорию 'Холодильники и морозильники'")
    @Severity(SeverityLevel.NORMAL)
    void selectCategory() {
        HomePage homePage = new HomePage(page);
        SearchPage searchPage = new SearchPage(page);

        homePage.open();
        homePage.selectPageCategory();

        assertEquals("https://5element.by/catalog/115-holodilniki-i-morozilniki", searchPage.getUrl());
    }

    @Test
    @Story("Работа с корзиной")
    @DisplayName("Добавление и удаление товара")
    @Severity(SeverityLevel.NORMAL)
    void cartOperations() {
        HomePage homePage = new HomePage(page);
        ShoppingCartPage shoppingCartPage = new ShoppingCartPage(page);

        homePage.open();
        homePage.search("Холодильник");

        shoppingCartPage.addProductToCart();
        shoppingCartPage.openShoppingCart();

        assertEquals(0, shoppingCartPage.getCartItemCount());

        shoppingCartPage.deleteProductFromCart();
        shoppingCartPage.openShoppingCart();
        assertTrue(shoppingCartPage.isCartEmptyMessageVisible());
    }
}