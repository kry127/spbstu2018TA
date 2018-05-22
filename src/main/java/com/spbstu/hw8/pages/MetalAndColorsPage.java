package com.spbstu.hw8.pages;

import com.epam.jdi.uitests.web.selenium.elements.common.Button;
import com.epam.jdi.uitests.web.selenium.elements.common.Label;
import com.epam.jdi.uitests.web.selenium.elements.complex.*;
import com.epam.jdi.uitests.web.selenium.elements.composite.WebPage;
import com.epam.jdi.uitests.web.selenium.elements.pageobjects.annotations.JPage;
import com.epam.jdi.uitests.web.selenium.elements.pageobjects.annotations.objects.JComboBox;
import com.spbstu.hw8.elements.ColorsEnum;
import com.spbstu.hw8.elements.ElementsEnum;
import com.spbstu.hw8.elements.NumberEnum;
import com.spbstu.hw8.elements.VegetableEnum;
import com.spbstu.hw8.entities.MetalAndColorEntity;
import org.openqa.selenium.By;
import org.openqa.selenium.WebElement;
import org.openqa.selenium.support.FindBy;
import ru.yandex.qatools.allure.annotations.Step;

import static com.codeborne.selenide.Condition.text;


@JPage(url = "/tests/page2.htm", title = "Metal and Colors")
public class MetalAndColorsPage extends WebPage {

    @FindBy(css="#odds-selector > .radio")
    private RadioButtons<NumberEnum> oddsSelector = null;

    @FindBy(css="#even-selector > .radio")
    private RadioButtons<NumberEnum> evenSelector = null;

    @FindBy(css="#elements-checklist > .checkbox > label")
    private CheckList<ElementsEnum> elements = null;

    @FindBy(id="submit-button")
    private Button submitButton = null;

    // результаты для проверки
    @FindBy(css="ul>li.summ-res")
    private Label summRes = null;
    @FindBy(css="ul>li.col-res")
    private Label colRes = null;
    @FindBy(css="ul>li.met-res")
    private Label metRes = null;
    @FindBy(css="ul>li.sal-res")
    private Label salRes = null;
    @FindBy(css="ul>li.elem-res")
    private Label elemRes = null;

    /*
     * Способ первый: стандартный FindBy селениума + листенер
     */
    @FindBy(css=".colors > .btn-group")
    private Dropdown<ColorsEnum> colors = new Dropdown<ColorsEnum>() {
        @Override
        protected void selectAction(String name) {
            expand();
            getDriver().findElements(By.cssSelector(".colors > .btn-group > div > ul > li"))
                .stream().filter(elm->name.equals(elm.getText())).forEach(WebElement::click);
        }
    };

    /*
     * Способ второй: сам всё ищи, отстань от меня. Тыкай туда, тыкай суда
     */
    @JComboBox(root = @FindBy(css = ".metals"),
            expand = @FindBy(css = ".caret"),
            list = @FindBy(tagName = "li"))
    private ComboBox metals = null;

    // третий способ -- с помощью конструктора
    private DropList<VegetableEnum> salad = new DropList<> (
            By.cssSelector("#salad-dropdown > ul > li"),
            By.cssSelector("#salad-dropdown > button"),
            By.cssSelector("#salad-dropdown > ul > li > a > label")
    );

    @Override
    @Step("Check Metals And Colors opened")
    public void checkOpened() {
        super.checkOpened();
    }

    @Step("Input Data")
    public void inputData(MetalAndColorEntity data) {
        for (int integer : data.summary){
            if (integer % 2 == 0) {
                evenSelector.select(String.valueOf(integer));
            } else {
                oddsSelector.select(String.valueOf(integer));
            }
        }
        for (String element : data.elements) {
            elements.select(element);
        }
        colors.select(data.color);
        metals.select(data.metals);

        // увы, пришлось воспользоваться таким решением
        // TODO переделать
        // возможно стоит переделать класс DropList, либо писать его обёртку (обёртка обёртки)
        openSalad(); // открываем салат
        salad.check(VegetableEnum.Salad); // снимаем галочку (не настроено чтение чекбоксов!!
        for (String vegetable : data.vegetables) {
            salad.check(vegetable);
        }

        submitButton.clickCenter();
    }

    private void openSalad() {
        salad.getAvatar()
                .getElement()
                .click();
    }

    @Step("Check Data Input")
    public void checkDataInput(MetalAndColorEntity data) {
        checkElements(data.elementsAsString());
        checkColor(data.colorAsString());
        checkVegetables(data.vegetablesAsString());
        checkMetals(data.metalsAsString());
        checkSum(data.summaryAsString());
    }

    @Step
    private void checkElements(String expected) {
        elemRes.shouldHave(text(expected));
    }
    @Step
    private void checkColor(String expected) {
        colRes.shouldHave(text(expected));
    }
    @Step
    private void checkVegetables(String expected) {
        salRes.shouldHave(text(expected));
    }
    @Step
    private void checkMetals(String expected) {
        metRes.shouldHave(text(expected));
    }
    @Step
    private void checkSum(String expected) {
        summRes.shouldHave(text(expected));
    }
}
