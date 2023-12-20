package ru.bikkul.compliment.telegram.bot.service;

import ru.bikkul.compliment.telegram.bot.dto.GoodMorningDto;

public interface WishesParserService {
    void saveWishesByDefaultSource();

    GoodMorningDto saveWishesByUser(GoodMorningDto goodMorningDto);
}

