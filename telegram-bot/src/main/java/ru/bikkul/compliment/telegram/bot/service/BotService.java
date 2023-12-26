package ru.bikkul.compliment.telegram.bot.service;

public interface BotService {
    void sendMessage(long chatId);

    void sendRandomWish(long chatId);

    void sendRandomPicture(long chatId);
}
