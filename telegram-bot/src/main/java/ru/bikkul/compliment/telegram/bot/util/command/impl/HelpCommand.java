package ru.bikkul.compliment.telegram.bot.util.command.impl;

import org.springframework.stereotype.Component;
import org.telegram.telegrambots.meta.api.objects.Message;
import ru.bikkul.compliment.telegram.bot.util.command.Command;
import ru.bikkul.compliment.telegram.bot.util.handler.MessageSender;

import static ru.bikkul.compliment.telegram.bot.util.common.BotConst.HELP_MESSAGE;

@Component
public class HelpCommand implements Command {
    private final MessageSender messageSender;

    public HelpCommand(MessageSender messageSender) {
        this.messageSender = messageSender;
    }

    @Override
    public void receivedCommand(Message message) {
        var chatId = message.getChatId();
        messageSender.sendMessage(chatId, HELP_MESSAGE);
    }
}
