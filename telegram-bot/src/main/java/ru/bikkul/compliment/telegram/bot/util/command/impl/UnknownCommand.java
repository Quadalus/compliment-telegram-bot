package ru.bikkul.compliment.telegram.bot.util.command.impl;

import org.springframework.stereotype.Component;
import org.telegram.telegrambots.meta.api.objects.Message;
import ru.bikkul.compliment.telegram.bot.util.command.Command;
import ru.bikkul.compliment.telegram.bot.util.handler.MessageSender;

@Component
public class UnknownCommand implements Command {
    private final MessageSender messageSender;

    public UnknownCommand(MessageSender messageSender) {
        this.messageSender = messageSender;
    }

    @Override
    public void receivedCommand(Message message) {
        Long chatId = message.getChatId();
        messageSender.sendMessage(chatId);
    }
}
