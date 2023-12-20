package ru.bikkul.compliment.telegram.bot.mapper;

import org.springframework.lang.NonNull;
import ru.bikkul.compliment.telegram.bot.dto.GoodMorningDto;
import ru.bikkul.compliment.telegram.bot.model.GoodMorning;

public class GoodMorningDtoMapper {
    private GoodMorningDtoMapper() {
    }

    public static GoodMorning fromDto(@NonNull GoodMorningDto goodMorningDto) {
        GoodMorning goodMorning = new GoodMorning();
        goodMorning.setText(goodMorningDto.text());
        goodMorning.setSource(goodMorningDto.source());
        return goodMorning;
    }

    public static GoodMorningDto toDto(@NonNull GoodMorning goodMorning) {
        String text = goodMorning.getText();
        String source = goodMorning.getSource();
        return new GoodMorningDto(text, source);
    }
}
