package ru.bikkul.compliment.telegram.bot.util.command.impl;

import org.springframework.stereotype.Component;
import org.telegram.telegrambots.meta.api.objects.Message;
import ru.bikkul.compliment.telegram.bot.util.command.Command;
import ru.bikkul.compliment.telegram.bot.util.common.UserTextCommands;
import ru.bikkul.compliment.telegram.bot.util.handler.MessageSender;

@Component
public class TimeCommand implements Command {
    private final MessageSender messageSender;

    public TimeCommand(MessageSender messageSender) {
        this.messageSender = messageSender;
    }

    @Override
    public void receivedCommand(Message message) {
        var text = "Введите новое время в формате часы:минуты";
        var chatId = message.getChatId();
        UserTextCommands.addCommand(chatId, "/time");
        messageSender.sendMessage(chatId, text);
    }
}
