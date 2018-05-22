package com.spbstu.hw8.elements;

import lombok.AllArgsConstructor;

@AllArgsConstructor
public enum HeaderMenu {
    HOME("HOME"),
    CONTACT_FORM("CONTACT FORM"),
    SERVICE("SERVICE"),
    METALS_N_COLORS("METALS & COLORS")
    ;
    /* Класс EnumUtils неявно обращается к этому полю, ипсользуя reflection.
     * Поле обязано иметь область видимости public (про final не уверен)
     */
    public final String value;
}
