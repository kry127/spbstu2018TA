package com.spbstu.hw8;

import com.google.gson.Gson;
import com.google.gson.reflect.TypeToken;
import com.spbstu.hw8.entities.MetalAndColorEntity;
import com.spbstu.hw8.entities.User;
import com.spbstu.hw8.entities.UserFactory;
import io.github.sskorol.core.DataSupplier;
import org.testng.annotations.Test;

import java.io.InputStream;
import java.io.InputStreamReader;
import java.lang.reflect.Type;
import java.util.Arrays;
import java.util.List;
import java.util.Map;
import java.util.stream.Stream;

public class TestCase extends BaseJDITest {

    @DataSupplier
    public Stream<MetalAndColorEntity> metalsColorsDatasetProvider() {
        Gson g = new Gson();
        InputStream stream = Gson.class.getClassLoader().getResourceAsStream("metalscolorsdataset.json");

        Type type = new TypeToken<Map<String, MetalAndColorEntity>>(){}.getType();
        Map<String, MetalAndColorEntity> entities = g.fromJson(new InputStreamReader(stream), type);
        return entities.values().stream();
    }

    @Test(dataProvider = "metalsColorsDatasetProvider")
    public void loginExample(MetalAndColorEntity entity) {
        JDIWebSite.homePage.open(); // открываем стандартную тестовую страницу
        User piter = UserFactory.getProfileUser(); // создаём сущность пользователя (по умолчанию Питер Чайловский)
        JDIWebSite.logout(); // перед входом в систему лучше всего выйти из неё
        JDIWebSite.login(piter); // регистрируемся в качестве полученного пользователяы
        JDIWebSite.homePage.checkOpened(); // проверяем, что страница открылась
        JDIWebSite.checkUserLoggedIn(piter); // проверяем, что после логина отображается соответствующее имя
        JDIWebSite.openMetalAndColors(); // открываем страницу металов и расцветок (с помощью меню!)
        JDIWebSite.metalAndColors.checkOpened(); // а уже с помощью Page Object проверяем, что всё открылось :)
        JDIWebSite.metalAndColors.inputData(entity);
    }
}
