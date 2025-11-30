package org.example.onlineshoptest.pages;

import com.microsoft.playwright.Locator;
import com.microsoft.playwright.Page;
import io.qameta.allure.Step;

public class HomePage {
    private final Page page;

    public HomePage(Page page) {
        this.page = page;
    }

    @Step("Открываем главную страницу")
    public void open() {
        page.navigate("https://5element.by/");
    }

    @Step("Выполняем поиск по слову: {query}")
    public void search(String text) {
        Locator searchInput = page.locator("//input[@class=\"inp inp--lg digi-instant-search jc-ignore\"]");
        searchInput.click();
        searchInput.fill(text);
        searchInput.press("Enter");
    }

    @Step("Переходим к категории 'Холодильники'")
    public void selectPageCategory() {
        page.locator("//a[@data-id=\"catalog-menu-closed\"]").click();
        page.locator("//a[@data-id=\"21-tehnika-dlya-kuhni\"]").click();
        page.locator("(//a[@class=\"c-index__item\"])[6]").click();
    }
}
