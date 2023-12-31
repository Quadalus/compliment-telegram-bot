package ru.bikkul.compliment.telegram.bot.util.command;

import org.telegram.telegrambots.meta.api.objects.Message;

public interface Command {
    void receivedCommand(Message message);
}
