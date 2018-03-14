package com.spbstu.selenium;

import org.openqa.selenium.*;
import org.openqa.selenium.chrome.ChromeDriver;
import org.openqa.selenium.support.ui.Wait;
import org.openqa.selenium.support.ui.WebDriverWait;
import org.testng.Assert;
import org.testng.annotations.AfterTest;
import org.testng.annotations.BeforeTest;
import org.testng.annotations.Test;
import org.testng.asserts.SoftAssert;

import java.util.List;
import java.util.stream.Collectors;

public class EpamSiteTestClass {

    // TODO make enum structure
    private static final String EPAM_TEST_LINK = "https://jdi-framework.github.io/tests";
    private static final String INDEX_PAGE = "Index Page";
    private static final String LOGIN = "epam";
    private static final String PASSWORD = "1234";
    private static final String DROPDOWN_CSS_SELECTOR = ".profile-photo";
    private static final String LOGIN_CSS_SELECTOR = "#Login";
    private static final String PASSWORD_CSS_SELECTOR = "#Password";
    private static final String USERNAME_CSS_SELECTOR = ".profile-photo > span";
    private static final String USER_FULL_DISPLAY_NAME = "PITER CHAILOVSKII";
    private static final String DIAGNOSTIC_STRING = "Actual: %s but expected: %s";
    private static final String ANNOTATION_CSS_SELECTOR = ".main-content > p";
    private static final String TITLE_CSS_SELECTOR = ".main-content > h3";
    private static final String EPAM_FRAMEWORK_WISHES = "EPAM FRAMEWORK WISHES…";
    private ChromeDriver driver;
    private SoftAssert sa;


    @BeforeTest
    public void beforeTest() {
        sa = new SoftAssert();
        driver = new ChromeDriver();
    }

    // TODO можно разбить на независимые тесты вместо SoftAssert, как вариант :)
    @Test
    public void epamLoginSimpleTest() throws InterruptedException {
        driver.navigate().to(EPAM_TEST_LINK);
        WebDriver f = null;
        // не ждём одну секунду, а делаем хитрее -- ждём, пока браузер загрузится
        // Ссылка на C# код, но полиморфизм сохранён:
        // https://stackoverflow.com/questions/5868439/wait-for-page-load-in-selenium
        Wait<WebDriver> wait = new WebDriverWait(driver, 5);
        wait.until(d -> ((JavascriptExecutor) d).executeScript("return document.readyState").equals("complete"));

        driver.manage().window().maximize();

        // Проверяем, что заголовок страницы совпадает с ожидаемым
        Assert.assertEquals(driver.getTitle(), INDEX_PAGE,
                String.format("Actual: %s but expected: %s", driver.getTitle(), INDEX_PAGE));

        // выполняем клик по иконке пользователя
        WebElement element = driver.findElement(By.cssSelector(DROPDOWN_CSS_SELECTOR));
        element.click();
        // заполняем поле логин
        element = driver.findElement(By.cssSelector(LOGIN_CSS_SELECTOR));
        element.sendKeys(LOGIN);
        // заполняем поле пароля и нажимаем клавишу Enter
        element = driver.findElement(By.cssSelector(PASSWORD_CSS_SELECTOR));
        element.sendKeys(PASSWORD + Keys.ENTER);

        // проверяем, что имя пользователя совпадает с ожидаемым
        element = driver.findElement(By.cssSelector(USERNAME_CSS_SELECTOR));
        element.getText();
        sa.assertEquals(element.getText(), USER_FULL_DISPLAY_NAME
                ,String.format(DIAGNOSTIC_STRING, driver.getTitle(), INDEX_PAGE));

        // получаем массив картинок, и смотрим, что их количество соответствует ожидаемому
        sa.assertEquals(driver.getTitle(), INDEX_PAGE
                ,String.format(DIAGNOSTIC_STRING, driver.getTitle(), INDEX_PAGE));
        int sz = driver.findElements(By.cssSelector(".benefit-icon")).size();
        sa.assertEquals(sz, 4
                ,String.format(DIAGNOSTIC_STRING, sz, 4));

        // проверяем совпадения текста под фотографиями
        // TODO -- грамотно вынести в константы. Негоже в 4 утра делать дз к паре
        // TODO -- порядок не важен, можно сделать через стримы
        String[] assertStrings = {"To include good practices\n" +
                "and ideas from successful\n" +
                "EPAM projec"
                ,"To be flexible and\n" +
                "customizable"
                ,"To be multiplatform"
                ,"Already have good base\n" +
                "(about 20 internal and\n" +
                "some external projects),\n" +
                "wish to get more…"};
        List<String> res = driver.findElements(By.cssSelector(".benefit-txt"))
                .stream().map(WebElement::getText).collect(Collectors.toList());
        // TODO вот тут сомнение, можно ли это было сделать через лямбды, не прибегая к циклам?
        // TODO Думаю, да, подумать об индексировании стрима средствами ФП
        for (int i = 0; i < assertStrings.length; i++) {
            sa.assertEquals(res.get(i), assertStrings[i], DIAGNOSTIC_STRING);
        }

        // проверяем совпадения заголовка текста в теге h3 с ожидаемым
        sa.assertEquals(driver.findElement(By.cssSelector(TITLE_CSS_SELECTOR)).getText(),
                EPAM_FRAMEWORK_WISHES, DIAGNOSTIC_STRING);

        // проверяем основное содержимое страницы на совпадение с ожидаемым
        sa.assertEquals(driver.findElement(By.cssSelector( ANNOTATION_CSS_SELECTOR)).getText(),
                "LOREM IPSUM DOLOR SIT AMET, CONSECTETUR ADIPISICING ELIT, SED DO EIUSMOD TEMPOR INCIDIDUNT UT LABORE ET DOLORE MAGNA ALIQUA. UT ENIM AD MINIM VENIAM, QUIS NOSTRUD EXERCITATION ULLAMCO LABORIS NISI UT ALIQUIP EX EA COMMODO CONSEQUAT DUIS AUTE IRURE DOLOR IN REPREHENDERIT IN VOLUPTATE VELIT ESSE CILLUM DOLORE EU FUGIAT NULLA PARIATUR."
                , DIAGNOSTIC_STRING);

        // выполняем SoftAssert
        sa.assertAll();
    }

    @AfterTest
    public void afterTest() {
        driver.close();
    }
}
