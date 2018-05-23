package com.spbstu.allure;

import com.epam.jdi.uitests.core.settings.JDISettings;
import com.spbstu.hw8.JDIWebSite;
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
        // selenide variant
        try {
            return ((TakesScreenshot) getWebDriver()).getScreenshotAs(OutputType.BYTES);
        } catch (Exception e) { e.printStackTrace();}
        return ((TakesScreenshot)JDISettings.driverFactory.getDriver()).getScreenshotAs(OutputType.BYTES);
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
