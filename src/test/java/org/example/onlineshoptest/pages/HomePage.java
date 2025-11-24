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
        Locator searchInput = page.locator("//*[@id=\"app\"]/header/div[2]/div[2]/div[1]/form/input");
        searchInput.click();
        searchInput.fill(text);
        searchInput.press("Enter");
    }

    @Step("Переходим к категории 'Холодильники'")
    public void selectPageCategory() {
        page.locator("//*[@id=\"app\"]/header/div[2]/div[2]/div[1]/a").click();
        page.locator("//*[@id=\"app\"]/catalog-menu/div[2]/div/div/div[1]/ul/li[5]/a").click();
        page.locator("//*[@id=\"app\"]/main/div/div/div/a[6]").click();
    }
}
