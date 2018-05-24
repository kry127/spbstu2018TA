package com.spbstu.selenide;

import com.codeborne.selenide.WebDriverProvider;
import com.codeborne.selenide.WebDriverRunner;
import com.spbstu.utils.PropertyLoader;
import lombok.SneakyThrows;
import org.openqa.selenium.WebDriver;
import org.openqa.selenium.remote.DesiredCapabilities;
import org.openqa.selenium.remote.RemoteWebDriver;

import java.net.URL;
public class CustomWebDriverProvider implements WebDriverProvider {


    @Override
    @SneakyThrows
    public WebDriver createDriver(DesiredCapabilities desiredCapabilities) {

        final URL url = new URL(PropertyLoader.get("driver.selenoid.hub"));

        /*ThreadLocal<WebDriver> webDriver = ThreadLocal.withInitial(() -> {
            RemoteWebDriver wd = new RemoteWebDriver(url, desiredCapabilities);
            wd.manage().window().maximize();
            return wd;
        });
        return webDriver.get();*/

        RemoteWebDriver wd = new RemoteWebDriver(url, desiredCapabilities);
        wd.manage().window().maximize();
        return wd;
    }
}
