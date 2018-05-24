package com.spbstu.selenide;

import com.codeborne.selenide.Configuration;
import com.codeborne.selenide.WebDriverRunner;
import com.codeborne.selenide.impl.WebDriverThreadLocalContainer;
import com.spbstu.utils.PropertyLoader;
import lombok.SneakyThrows;
import org.openqa.selenium.remote.DesiredCapabilities;
import org.openqa.selenium.remote.RemoteWebDriver;
import org.testng.annotations.BeforeSuite;

import java.net.URL;

public class BaseSelenideTest {

    @BeforeSuite
    public void beforeSuite() {
        Configuration.browser = "CHROME";
        Configuration.startMaximized = true;
        Configuration.timeout = 4000;
        Configuration.pollingInterval = 100; // "-Dselenide.pollingInterval=50"
        Configuration.collectionsPollingInterval = 300;

        Configuration.reportsFolder = "build/reports/tests"; // в неё складываются скриншоты

    }
}
