package org.example.onlineshoptest.pages;

import com.microsoft.playwright.Page;

public class SearchPage {
    private final Page page;

    public SearchPage(Page page) {
        this.page = page;
    }

    public String getUrl() {
        return page.url();
    }
}
