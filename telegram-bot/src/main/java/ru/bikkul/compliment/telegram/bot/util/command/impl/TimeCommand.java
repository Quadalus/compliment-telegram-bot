package ru.bikkul.compliment.telegram.bot.util.command.impl;

import org.springframework.stereotype.Component;
import org.telegram.telegrambots.meta.api.objects.Message;
import ru.bikkul.compliment.telegram.bot.util.command.Command;
import ru.bikkul.compliment.telegram.bot.util.common.UserTextCommands;
import ru.bikkul.compliment.telegram.bot.util.handler.MessageHandler;

@Component
public class TimeCommand implements Command {
    private final MessageHandler messageHandler;

    public TimeCommand(MessageHandler messageHandler) {
        this.messageHandler = messageHandler;
    }

    @Override
    public void receivedCommand(Message message) {
        var text = "Введите новое время в формате часы:минуты";
        var chatId = message.getChatId();
        UserTextCommands.addCommand(chatId, "/time");
        messageHandler.sendMessage(chatId, text);
    }
}
