package ru.bikkul.compliment.telegram.bot.mapper;

import ru.bikkul.compliment.telegram.bot.dto.GoodMorningDto;
import ru.bikkul.compliment.telegram.bot.model.GoodMorning;

public class GoodMorningDtoMapper {
    private GoodMorningDtoMapper() {
    }

    public static GoodMorning fromDto(GoodMorningDto goodMorningDto) {
        GoodMorning goodMorning = new GoodMorning();
        goodMorning.setText(goodMorningDto.text());
        return goodMorning;
    }
}
