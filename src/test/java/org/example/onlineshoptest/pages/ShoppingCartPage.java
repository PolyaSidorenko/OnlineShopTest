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
        page.locator("(//div[@class=\"h-drop__head\"])[5]").click();
    }

    @Step("Добавляем товар в корзину")
    public void addProductToCart() {
        page.locator("(//a[@data-id=\"add-product-to-cart\"])[1]").click();
    }

    @Step("Удаляем товары из корзины")
    public void deleteProductFromCart() {
        page.locator("//a[@class=\"remove-all\"]").click();
    }

    @Step("Получаем количество товаров в корзине")
    public int getCartItemCount() {
        String countText = page.locator("//span[@class=\"checkout-count\"]").textContent();
        if (countText.equals("null")) {
            return 0;
        }
        return Integer.parseInt(countText.trim());
    }

    @Step("Получаем итоговую сумму заказа")
    public String getTotalPrice() {
        Locator totalBlock = page.locator("//div[@class=\"payment-info__row payment-info__total\"]");
        String priceText = totalBlock.locator("div").nth(1).textContent();
        return priceText.trim();
    }

    @Step("Проверяем сообщение о пустой корзине")
    public boolean isCartEmptyMessageVisible() {
        page.waitForSelector("#app > main > div > checkout-page > div > h2",
                new Page.WaitForSelectorOptions().setTimeout(5000));
        return page.locator("//h2[text()=\"В корзине еще нет товаров\"]").isVisible();
    }
}
