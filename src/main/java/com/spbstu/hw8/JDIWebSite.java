package com.spbstu.hw8;

import com.epam.jdi.uitests.web.selenium.elements.common.Button;
import com.epam.jdi.uitests.web.selenium.elements.common.Label;
import com.epam.jdi.uitests.web.selenium.elements.complex.Menu;
import com.epam.jdi.uitests.web.selenium.elements.composite.Form;
import com.epam.jdi.uitests.web.selenium.elements.composite.WebSite;
import com.epam.jdi.uitests.web.selenium.elements.pageobjects.annotations.JSite;
import com.epam.web.matcher.testng.Assert;
import com.spbstu.hw8.elements.HeaderMenu;
import com.spbstu.hw8.entities.User;
import com.spbstu.hw8.pages.HomePage;
import com.spbstu.hw8.pages.LoginForm;
import com.spbstu.hw8.pages.MetalAndColorsPage;
import org.openqa.selenium.support.FindBy;

@JSite("https://jdi-framework.github.io/")
public class JDIWebSite extends WebSite {

    public static HomePage homePage;
    public static LoginForm loginForm;
    public static MetalAndColorsPage metalAndColors;

    @FindBy(css = ".profile-photo")
    public static Label profilePhoto;

    @FindBy(css = ".uui-navigation > li > a")
    public static Menu<HeaderMenu> headerMenu;

    @FindBy(css = ".logout > .btn-login")
    public static Button logoutButton;

    @FindBy(css = ".dropdown-menu-login")
    public static Form<?> dropdownMenuLogin;

    public static void logout() {
        if (!logoutButton.isDisplayed()) {
            profilePhoto.click();
        }
        if (logoutButton.isDisplayed()) {
            logoutButton.click();
        }
    }

    public static void login(User user) {
        if (!dropdownMenuLogin.isDisplayed())
            profilePhoto.click();
        loginForm.loginAs(user);
    }

    public static void checkUserLoggedIn(User user) {
        //profilePhoto.shouldHave(text(user.displayName)); //selenide style
        Assert.areEquals(profilePhoto.getText(), user.displayName); // jdi
    }

    public static void openMetalAndColors() {
        headerMenu.clickOn(HeaderMenu.METALS_N_COLORS);
    }
}
