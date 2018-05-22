package com.spbstu.hw8.pages;

import com.epam.jdi.uitests.core.interfaces.complex.IPage;
import com.epam.jdi.uitests.web.selenium.elements.composite.WebPage;
import com.epam.jdi.uitests.web.selenium.elements.pageobjects.annotations.JPage;
import ru.yandex.qatools.allure.annotations.Step;

@JPage(url = "/tests/index.htm", title = "Index Page")
public class HomePage extends WebPage {
    @Override
    @Step("Check home page opened")
    public <T extends IPage> T open() {
        return super.open();
    }
}
