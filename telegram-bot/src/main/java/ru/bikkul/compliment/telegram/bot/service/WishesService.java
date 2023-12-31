package ru.bikkul.compliment.telegram.bot.service;

public interface WishesService {
    String getRandomWish();

    void sendRandomWish(long chatId);
}
