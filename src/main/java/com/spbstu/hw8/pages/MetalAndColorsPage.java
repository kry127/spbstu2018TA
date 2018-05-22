package com.spbstu.hw8.pages;

import com.epam.jdi.uitests.web.selenium.elements.complex.*;
import com.epam.jdi.uitests.web.selenium.elements.composite.WebPage;
import com.epam.jdi.uitests.web.selenium.elements.pageobjects.annotations.JFindBy;
import com.epam.jdi.uitests.web.selenium.elements.pageobjects.annotations.JPage;
import com.epam.jdi.uitests.web.selenium.elements.pageobjects.annotations.objects.JComboBox;
import com.epam.jdi.uitests.web.selenium.elements.pageobjects.annotations.objects.JDropdown;
import com.spbstu.hw8.elements.ColorsEnum;
import com.spbstu.hw8.elements.ElementsEnum;
import com.spbstu.hw8.elements.MetalsEnum;
import com.spbstu.hw8.elements.NumberEnum;
import com.spbstu.hw8.entities.MetalAndColorEntity;
import lombok.val;
import org.openqa.selenium.By;
import org.openqa.selenium.WebElement;
import org.openqa.selenium.support.FindBy;


@JPage(url = "/tests/page2.htm", title = "Metal and Colors")
public class MetalAndColorsPage extends WebPage {

    @FindBy(css="#odds-selector > .radio")
    RadioButtons<NumberEnum> oddsSelector;

    @FindBy(css="#even-selector > .radio")
    RadioButtons<NumberEnum> evenSelector;

    @FindBy(css="#elements-checklist > .checkbox > label")
    CheckList<ElementsEnum> elements;

    @FindBy(css=".colors > .btn-group")
    Dropdown<ColorsEnum> colors = new Dropdown<ColorsEnum>() {
        @Override
        protected void selectAction(String name) {
            expand();
            getDriver().findElements(By.cssSelector(".colors > .btn-group > div > ul > li"))
                .stream().filter(elm->name.equals(elm.getText())).forEach(WebElement::click);
            //close();
        }
    };

    //@FindBy(css=".metals > .btn-group > div > ul > li")
    /*@JComboBox(
            jroot = @JFindBy(css = ".metals > .btn-group "),
            jlist = @JFindBy(css = ".metals > .btn-group > div > ul"),
            jexpand = @JFindBy(css = ".metals > .btn-group > button"),
            jvalue = @JFindBy(css = ".metals > .btn-group > div > ul > li")
    )*/
    ComboBox<MetalsEnum> metals = new ComboBox<MetalsEnum>(
            By.cssSelector(".metals > .btn-group > button > .caret"),
            By.cssSelector(".metals > .btn-group > div > ul > li"),
            By.cssSelector(".metals > .btn-group > div > ul > li"),
            By.cssSelector(".metals > .btn-group > div > ul > li")
    );

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
    }
}
