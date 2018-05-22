package com.spbstu.hw8.entities;

import lombok.AllArgsConstructor;
import lombok.Getter;

@AllArgsConstructor
public class User {
    // по умолчанию поля инициализируются с помощью Properties (а они зависят от профиля maven)
    @Getter
    public String displayName;
    public String name;
    public String password;
}