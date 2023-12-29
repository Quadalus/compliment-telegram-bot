package ru.bikkul.compliment.telegram.bot.service;

import org.springframework.scheduling.annotation.EnableAsync;
import org.telegram.telegrambots.meta.api.objects.Update;

import java.io.File;
import java.util.concurrent.CompletableFuture;

@EnableAsync
public interface BotService {
    void sendMessage(long chatId);

    void sendRandomWish(long chatId);

    void sendRandomPicture(long chatId);
}
