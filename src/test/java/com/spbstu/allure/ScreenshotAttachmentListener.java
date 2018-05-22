package com.spbstu.allure;

import org.openqa.selenium.OutputType;
import org.openqa.selenium.TakesScreenshot;
import org.testng.ITestResult;
import org.testng.TestListenerAdapter;
import ru.yandex.qatools.allure.annotations.Attachment;

import static com.codeborne.selenide.WebDriverRunner.getWebDriver;

public class ScreenshotAttachmentListener extends TestListenerAdapter {

    @Attachment
    private byte[] makeScreenshot() {
        byte[] array = {1};
        return ((TakesScreenshot) getWebDriver()).getScreenshotAs(OutputType.BYTES);
    }

    @Override
    public void onTestFailure(ITestResult res) {
        byte[] inf = makeScreenshot();
    }

    @Override
    public void onTestSkipped(ITestResult iTestResult) {
        byte[] inf = makeScreenshot();
    }
}
