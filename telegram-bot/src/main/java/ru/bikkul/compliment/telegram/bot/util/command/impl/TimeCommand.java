package ru.bikkul.compliment.telegram.bot.util.command.impl;

import org.springframework.stereotype.Component;
import org.telegram.telegrambots.meta.api.objects.Message;
import ru.bikkul.compliment.telegram.bot.service.UserSettingService;
import ru.bikkul.compliment.telegram.bot.util.command.Command;
import ru.bikkul.compliment.telegram.bot.util.handler.MessageHandler;
import ru.bikkul.compliment.telegram.bot.util.quartz.TelegramScheduler;

@Component
public class TimeCommand implements Command {
    private final MessageHandler messageHandler;
    private final UserSettingService userSettingService;
    private final TelegramScheduler telegramScheduler;

    public TimeCommand(MessageHandler messageHandler, UserSettingService userSettingService, TelegramScheduler telegramScheduler) {
        this.messageHandler = messageHandler;
        this.userSettingService = userSettingService;
        this.telegramScheduler = telegramScheduler;
    }

    @Override
    public void receivedCommand(Message message) {
        var text = "Введите новое время в формате часы:минуты";
        var chatId = message.getChatId();
        UserTextCommands.addCommand(chatId, "/time");
        messageHandler.sendMessage(chatId, text);
    }
}
