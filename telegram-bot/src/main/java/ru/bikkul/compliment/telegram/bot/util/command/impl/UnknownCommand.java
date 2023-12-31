package ru.bikkul.compliment.telegram.bot.util.command.impl;

import org.springframework.stereotype.Component;
import org.telegram.telegrambots.meta.api.objects.Message;
import ru.bikkul.compliment.telegram.bot.util.command.Command;
import ru.bikkul.compliment.telegram.bot.util.handler.MessageHandler;

@Component
public class UnknownCommand implements Command {
    private final MessageHandler messageHandler;

    public UnknownCommand(MessageHandler messageHandler) {
        this.messageHandler = messageHandler;
    }

    @Override
    public void receivedCommand(Message message) {
        Long chatId = message.getChatId();
        messageHandler.sendMessage(chatId);
    }
}
