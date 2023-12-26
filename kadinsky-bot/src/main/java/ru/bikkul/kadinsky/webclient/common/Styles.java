package ru.bikkul.kadinsky.webclient.common;

import lombok.Getter;

@Getter
public enum Styles {
    DEFAULT("DEFAULT"),
    KANDINSKY("KANDINSKY"),
    UHD("UHD"),
    ANIME("ANIME");

    private final String style;

    Styles(String style) {
        this.style = style;
    }
}
