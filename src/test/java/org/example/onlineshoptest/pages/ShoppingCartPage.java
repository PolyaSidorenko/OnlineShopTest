package org.example.onlineshoptest.pages;

import com.microsoft.playwright.Locator;
import com.microsoft.playwright.Page;
import io.qameta.allure.Step;

public class ShoppingCartPage {
    private final Page page;

    public ShoppingCartPage(Page page) {
        this.page = page;
    }

    @Step("Открываем корзину")
    public void openShoppingCart() {
        page.locator("//*[@id=\"app\"]/header/div[2]/div[2]/div[2]/base-header-button[3]/a/div").click();
    }

    @Step("Добавляем товар в корзину")
    public void addProductToCart() {
        page.locator("//*[@id=\"app\"]/main/div/div[1]/div[3]/div[3]/div[1]/div[1]/div[3]/div/div[4]/div[1]/add-to-cart-button/a").click();
    }

    @Step("Удаляем товары из корзины")
    public void deleteProductFromCart() {
        page.locator("//*[@id=\"app\"]/main/div/checkout-page/div/div[1]/div[1]/div[1]/a").click();
    }

    @Step("Получаем количество товаров в корзине")
    public int getCartItemCount() {
        String countText = page.locator("//*[@id=\"app\"]/main/div/checkout-page/div/div[1]/h1/span").textContent();
        if (countText.equals("null")) {
            return 0;
        }
        return Integer.parseInt(countText.trim());
    }

    @Step("Получаем итоговую сумму заказа")
    public String getTotalPrice() {
        Locator totalBlock = page.locator("//*[@id=\"app\"]/main/div/checkout-page/div/div[2]/div[2]/div[2]/div[8]/div[2]");
        String priceText = totalBlock.locator("div").nth(1).textContent();
        return priceText.trim();
    }

    @Step("Проверяем сообщение о пустой корзине")
    public boolean isCartEmptyMessageVisible() {
        page.waitForSelector("#app > main > div > checkout-page > div > h2",
                new Page.WaitForSelectorOptions().setTimeout(5000));
        return page.locator("//*[@id=\"app\"]/main/div/checkout-page/div/h2").isVisible();
    }
}
