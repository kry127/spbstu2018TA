package com.spbstu.hw8;

import com.epam.jdi.uitests.core.logger.JDILogger;
import com.epam.jdi.uitests.core.settings.JDISettings;
import com.epam.jdi.uitests.web.selenium.driver.SeleniumDriverFactory;
import com.epam.jdi.uitests.web.selenium.elements.composite.WebSite;
import com.epam.jdi.uitests.web.settings.WebSettings;
import com.spbstu.allure.ScreenshotAttachmentListener;
import com.spbstu.utils.PropertyLoader;
import lombok.SneakyThrows;
import org.openqa.selenium.WebDriver;
import org.slf4j.MarkerFactory;
import org.testng.annotations.AfterSuite;
import org.testng.annotations.BeforeSuite;
import org.testng.annotations.Listeners;

import static com.epam.jdi.uitests.core.settings.JDISettings.logger;


@Listeners(ScreenshotAttachmentListener.class)
public class BaseJDITest {

    public static final String TEST_INFO = "TEST_INFO";
    WebDriver driver;

    @BeforeSuite(alwaysRun = true)
    @SneakyThrows
    public void setUp() {

        // почему-то без этой строки не работает, и поля настроечной структуры
        // JDISettings то инициализируются, то имеют на следующем шаге выполнения кода значение null
        WebSettings.init();

        // указываем JDI путь до cromedriver.exe (ну, или другой драйвер, который может использоваться)
        JDISettings.driverFactory.setDriverPath(PropertyLoader.get("driver.path"));

        // дальше как в примере с github
        WebSite.init(JDIWebSite.class); // инициализируем сайт целиком (PageObjects JDI проинициализирует сам рекурс.)
        driver = (WebDriver)JDISettings.driverFactory.getDriver(); // получаем новый инстанс браузера
        logger.info(MarkerFactory.getMarker(TEST_INFO), "Run tests");// оповещаем пользователя о начале тестов
    }

    @AfterSuite(alwaysRun = true)
    public void tearDown() {
        logger.info(MarkerFactory.getMarker(TEST_INFO), "Tear down tests");
        driver.close(); // необходимо закрывать браузер после проведения тестов
    }
}
