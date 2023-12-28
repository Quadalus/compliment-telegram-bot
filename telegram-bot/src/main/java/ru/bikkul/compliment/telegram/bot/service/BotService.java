package ru.bikkul.compliment.telegram.bot.service;

import org.springframework.scheduling.annotation.EnableAsync;
import org.telegram.telegrambots.meta.api.objects.Update;

@EnableAsync
public interface BotService {
    void sendMessage(long chatId);

    void sendRandomWish(long chatId);

    void sendRandomPicture(long chatId);

    void sendRandomPictureWithoutCaption(long chatId);

    void sendRandomReceived(long chatId);

    void receivedCallback(Update update, long chatId);
}
