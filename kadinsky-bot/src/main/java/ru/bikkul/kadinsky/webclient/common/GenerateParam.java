package ru.bikkul.kadinsky.webclient.common;

import lombok.Getter;

import java.util.List;

@Getter
public enum GenerateParam {
    LOCATION(List.of("город", "море", "океан", "деревня", "горы", "лес", "пустыня", "комната")),
    WEATHER(List.of("дождь", "солнце", "туман", "снег", "облака", "рассвет")),
    OTHER(List.of("кофе", "чай", "утки", "будильник", "завтрак", "кошка" ,"морские котики", "голуби", "сакура", "цветы")),
    OTHER_WATER(List.of("корабль", "рыбы", "кораллы"));

    GenerateParam(List<String> params) {
        this.params = params;
    }

    private final List<String> params;
}
