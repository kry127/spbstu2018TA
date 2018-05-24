package com.spbstu.hw4;

import com.codeborne.selenide.Selenide;
import com.codeborne.selenide.WebDriverRunner;
import com.spbstu.allure.ScreenshotAttachmentListener;
import com.spbstu.hw4.utils.Color;
import com.spbstu.hw4.utils.Element;
import com.spbstu.hw4.utils.Metal;
import com.spbstu.selenide.BaseSelenideTest;
import com.spbstu.utils.PropertyLoader;
import lombok.SneakyThrows;
import org.openqa.selenium.WebDriver;
import org.openqa.selenium.remote.DesiredCapabilities;
import org.openqa.selenium.remote.RemoteWebDriver;
import org.testng.annotations.BeforeClass;
import org.testng.annotations.BeforeTest;
import org.testng.annotations.Listeners;
import org.testng.annotations.Test;
import ru.yandex.qatools.allure.annotations.Features;
import ru.yandex.qatools.allure.annotations.Stories;

import java.net.URL;
import java.util.Arrays;
import java.util.List;

import static com.spbstu.hw4.HomeWork4Site.elementsPage;
import static com.spbstu.hw4.HomeWork4Site.homePage;
import static com.spbstu.utils.PropertyLoader.get;

@Listeners(ScreenshotAttachmentListener.class)
@Features({"Selenide Test Suite"})
@Stories({"Elements tests"})
public class TestCase1 extends BaseSelenideTest {
    private static final String TEST_USER_NAME = "test.user.name";
    private static final String TEST_USER_PASSWORD = "test.user.password";
    private static final String TEST_USER_DISPLAY_NAME = "test.user.displayName";
    private static final List<String> SUBMENU_ITEMS = Arrays.asList("Support", "Dates", "Complex Table", "Simple Table", "Table with pages", "Different elements");
    private static final String TEST_SITE_HOMEPAGE = "test.site.homepage";

    @BeforeClass
    @SneakyThrows
    public void beforeClass() {
        HomeWork4Site.init();
    }
    /**
     * Данный тест можно запустить следующими maven командами:
     * mvn clean package -Phome_work_4_runner
     * mvn verify -Phome_work_4_runner
     */
    @Test
    public void test() {
        homePage.open(get(TEST_SITE_HOMEPAGE));
        homePage.logout();
        homePage.login(get(TEST_USER_NAME), get(TEST_USER_PASSWORD));
        homePage.checkUserLogIn(get(TEST_USER_DISPLAY_NAME));
        homePage.checkInterface();

        List<String> items = SUBMENU_ITEMS;
        homePage.openHeaderSubmenu();
        homePage.checkHeaderSubmenu(items);
        homePage.openSidebarSubmenu();
        homePage.checkSidebarSubmenu(items);
        homePage.openElementsPage();
        // второй вариант, как можно перейти на страницу: непосредственное открытие
        //HomeWork4Site.elementsPage.open(get("test.site.elementspage"));


        elementsPage.checkPage();
        elementsPage.selectElement(Element.WATER, true);
        elementsPage.selectElement(Element.WIND, true);
        elementsPage.selectMetal(Metal.SELEN);
        elementsPage.selectColor(Color.YELLOW);
        // Check in logs section selected values and status (true|false)
        // HINT: generate your own logs and check is a subset
        elementsPage.checkLogOutput();
        elementsPage.selectElement(Element.WATER, false);
        elementsPage.selectElement(Element.WIND, false);
        // Check again
        elementsPage.checkLogOutput();

        Selenide.Wait();
    }
}
