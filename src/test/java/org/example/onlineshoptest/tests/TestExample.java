package org.example.onlineshoptest.tests;

import com.microsoft.playwright.Browser;
import com.microsoft.playwright.BrowserContext;
import com.microsoft.playwright.Page;
import com.microsoft.playwright.Playwright;
import io.qameta.allure.Allure;
import io.qameta.allure.Description;
import io.qameta.allure.Epic;
import io.qameta.allure.Severity;
import io.qameta.allure.SeverityLevel;
import io.qameta.allure.Story;
import org.example.onlineshoptest.pages.HomePage;
import org.example.onlineshoptest.pages.SearchPage;
import org.example.onlineshoptest.pages.ShoppingCartPage;
import org.junit.jupiter.api.AfterAll;
import org.junit.jupiter.api.AfterEach;
import org.junit.jupiter.api.BeforeAll;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.DisplayName;
import org.junit.jupiter.api.Test;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

import java.io.ByteArrayInputStream;

import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.junit.jupiter.api.Assertions.assertTrue;

@Epic("UI Tests")
public class TestExample {
    private static final Logger log = LoggerFactory.getLogger(TestExample.class);
    static Playwright playwright;
    static Browser browser;

    BrowserContext context;
    Page page;

    @BeforeAll
    static void launchBrowser() {
        playwright = Playwright.create();
        browser = playwright.chromium().launch();
    }

    @AfterAll
    static void closeBrowser() {
        playwright.close();
    }

    @BeforeEach
    void createContextAndPage() {
        context = browser.newContext();
        page = context.newPage();
    }

    @AfterEach
    void closeContext() {
        context.close();
    }

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

        attachScreenshot("Результаты поиска 'Холодильник'");
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

        assertEquals("https://5element.by/catalog/115-holodilniki-i-morozilniki", searchPage.getUrl() );

        attachScreenshot("Категория 'холодильники'");
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

        assertEquals(1, shoppingCartPage.getCartItemCount());

        attachScreenshot("Добавление в корзину");

        shoppingCartPage.deleteProductFromCart();
        shoppingCartPage.openShoppingCart();
        assertTrue(shoppingCartPage.isCartEmptyMessageVisible());

        attachScreenshot("Удаление из корзины");
    }

    public void attachScreenshot(String name) {
        byte[] screenshot = page.screenshot(new Page.ScreenshotOptions().setFullPage(true));
        Allure.addAttachment(name, new ByteArrayInputStream(screenshot));
    }
}