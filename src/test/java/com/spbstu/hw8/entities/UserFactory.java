package com.spbstu.hw8.entities;

import ru.yandex.qatools.allure.annotations.Step;

import static com.spbstu.utils.PropertyLoader.get;

public class UserFactory {
    /**
     * Получаем пользователя, указанного (как правило) в профиле Maven
     * @return PETER CHAILOVSKII
     */
    @Step
    public static User getProfileUser() {
        return new User(get("test.user.displayName"), get("test.user.name"), get("test.user.password"));
    }
}
