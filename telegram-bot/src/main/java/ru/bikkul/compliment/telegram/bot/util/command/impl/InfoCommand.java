package ru.bikkul.compliment.telegram.bot.util.command.impl;

import org.springframework.stereotype.Component;
import org.telegram.telegrambots.meta.api.objects.Message;
import ru.bikkul.compliment.telegram.bot.util.command.Command;
import ru.bikkul.compliment.telegram.bot.util.enums.SourceType;
import ru.bikkul.compliment.telegram.bot.util.quartz.TelegramScheduler;
import ru.bikkul.compliment.telegram.bot.util.handler.MessageHandler;

@Component
public class InfoCommand implements Command {
    private final MessageHandler messageHandler;
    private final TelegramScheduler telegramScheduler;

    public InfoCommand(MessageHandler messageHandler, TelegramScheduler telegramScheduler) {
        this.messageHandler = messageHandler;
        this.telegramScheduler = telegramScheduler;
    }

    @Override
    public void receivedCommand(Message message) {
        var chatId = message.getChatId();
        var time = telegramScheduler.getCronTime(chatId);

        var source = SourceType.SITE;

        var text = """
                Ваши настройки:
                 - Время уведомлений: %s
                 - Источник утренних желаний: %s
                """.formatted(time, source);
        messageHandler.sendMessage(chatId, text);
    }
}
