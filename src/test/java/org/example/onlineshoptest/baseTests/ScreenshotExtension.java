package org.example.onlineshoptest.baseTests;

import com.microsoft.playwright.Page;
import io.qameta.allure.Allure;
import org.junit.jupiter.api.extension.ExtensionContext;
import org.junit.jupiter.api.extension.TestExecutionExceptionHandler;

import java.io.ByteArrayInputStream;

public class ScreenshotExtension implements TestExecutionExceptionHandler {
//делаем скриншот при падении теста
    @Override
    public void handleTestExecutionException(ExtensionContext context, Throwable throwable) throws Throwable {
        Object testInstance = context.getRequiredTestInstance();
        if (testInstance instanceof BaseUITest) {
            Page page = ((BaseUITest) testInstance).page;
            if (page != null) {
                try {
                    byte[] screenshot = page.screenshot(new Page.ScreenshotOptions().setFullPage(true));
                    Allure.addAttachment("Screenshot on failure", new ByteArrayInputStream(screenshot));
                } catch (Exception e) {
                    System.out.println(e.getMessage());
                }
            }
        }
        throw throwable;
    }
}
