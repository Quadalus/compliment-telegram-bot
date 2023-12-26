package ru.bikkul.kadinsky.webclient.common;

import lombok.Getter;

@Getter
public enum DefaultStyles {
    CYBERPUNK("киберпанк"),
    OIL_PAINTING("картины маслом"),
    MALEVICH("малевича"),
    AIVAZOVSKY("айвазовского"),
    PORTRAIT_PHOTO("портретного фото"),
    PIXEL("пиксель арт"),
    CLASSICISM("классицизма"),
    DIGITAL("цифровой живописи"),
    CARTON("мультфильма"),
    WATER_COLOUR("акварели");
    
    private final String defaultStyle;

    DefaultStyles(String defaultStyle) {
        this.defaultStyle = defaultStyle;
    }
}
