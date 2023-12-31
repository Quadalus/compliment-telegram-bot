package ru.bikkul.compliment.telegram.bot.util.command.impl;

import org.springframework.stereotype.Component;
import org.telegram.telegrambots.meta.api.objects.Message;
import ru.bikkul.compliment.telegram.bot.util.command.Command;
import ru.bikkul.compliment.telegram.bot.util.handler.MessageHandler;

import static ru.bikkul.compliment.telegram.bot.util.common.BotConst.HELP_MESSAGE;

@Component
public class HelpCommand implements Command {
    private final MessageHandler messageHandler;

    public HelpCommand(MessageHandler messageHandler) {
        this.messageHandler = messageHandler;
    }

    @Override
    public void receivedCommand(Message message) {
        var chatId = message.getChatId();
        messageHandler.sendMessage(chatId, HELP_MESSAGE);
    }
}
