package com.spbstu.hw8;

import com.epam.jdi.uitests.core.settings.JDISettings;
import com.epam.jdi.uitests.web.selenium.elements.composite.WebSite;
import com.epam.jdi.uitests.web.settings.WebSettings;
import com.spbstu.utils.PropertyLoader;
import com.sun.jndi.toolkit.url.Uri;
import lombok.SneakyThrows;
import org.openqa.selenium.WebDriver;
import org.openqa.selenium.remote.DesiredCapabilities;
import org.openqa.selenium.remote.RemoteWebDriver;
import org.slf4j.MarkerFactory;
import org.testng.annotations.AfterSuite;
import org.testng.annotations.BeforeSuite;

import java.net.URL;
import java.util.function.Supplier;

import static com.epam.jdi.uitests.core.settings.JDISettings.logger;


public class BaseJDITest {

    public static final String TEST_INFO = "TEST_INFO";
    //WebDriver driver;

    @BeforeSuite(alwaysRun = true)
    @SneakyThrows
    public void setUp() {

        // почему-то без этой строки не работает, и поля настроечной структуры
        // JDISettings то инициализируются, то имеют на следующем шаге выполнения кода значение null
        WebSettings.init();

        // указываем JDI путь до cromedriver.exe (ну, или другой драйвер, который может использоваться)
        final URL url = new URL(PropertyLoader.get("driver.selenoid.hub"));
        WebSettings.useDriver(() -> {
            RemoteWebDriver wd = new RemoteWebDriver(url, DesiredCapabilities.chrome());
            return wd;
        });



        //JDISettings.driverFactory.setDriverPath(PropertyLoader.get("driver.path"));
        //driver = (WebDriver)JDISettings.driverFactory.getDriver("http://localhost:4444/wd/hub");

        // дальше как в примере с github
        WebSite.init(JDIWebSite.class); // инициализируем сайт целиком (PageObjects JDI проинициализирует сам рекурс.)
        driver = (WebDriver)JDISettings.driverFactory.getDriver(); // получаем новый инстанс браузера
        logger.info(MarkerFactory.getMarker(TEST_INFO), "Run tests");// оповещаем пользователя о начале тестов
    }

    @AfterSuite(alwaysRun = true)
    public void tearDown() {
        logger.info(MarkerFactory.getMarker(TEST_INFO), "Tear down tests");
        /*WebSettings.getDriver().close(); // необходимо закрывать браузер после проведения тестов*/}
}
