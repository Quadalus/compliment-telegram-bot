package ru.bikkul.compliment.telegram.bot.service;

import org.telegram.telegrambots.meta.api.objects.Update;

public interface CommandHandlerService {
    void receivedCommand(final Update update);
}
