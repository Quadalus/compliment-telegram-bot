package ru.bikkul.kadinsky.webclient.common;

import lombok.Getter;

@Getter
public enum DefaultStyles {
    CYBERPUNK("киберпанк"),
    OIL_PAINTING("картины маслом"),
    MALEVICH("малевича"),
    AIVAZOVSKY("айвазовского"),
    PORTRAIT_PHOTO("портретного фото"),
    PIXEL("пиксель арта"),
    CLASSICISM("классицизма"),
    DIGITAL("цифровой живописи"),
    CARTON("мультфильма"),
    WATER_COLOUR("акварели"),
    REPIN("репина"),
    VAN_GOG("ван гога"),
    JAPAN("японской гравюры"),
    MINIMALISM("минимализма"),
    POP_ART("поп арта");
    
    private final String defaultStyle;

    DefaultStyles(String defaultStyle) {
        this.defaultStyle = defaultStyle;
    }
}
