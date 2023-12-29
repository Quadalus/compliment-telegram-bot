package ru.bikkul.compliment.telegram.bot.service;

import org.springframework.scheduling.annotation.EnableAsync;

@EnableAsync
public interface BotService {
    void sendMessage(long chatId);

    void sendRandomWish(long chatId);

    void sendRandomPicture(long chatId);
}
