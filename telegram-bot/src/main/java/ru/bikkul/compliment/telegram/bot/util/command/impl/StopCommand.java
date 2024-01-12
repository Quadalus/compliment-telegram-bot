package ru.bikkul.compliment.telegram.bot.util.command.impl;

import lombok.extern.slf4j.Slf4j;
import org.springframework.stereotype.Component;
import org.telegram.telegrambots.meta.api.objects.Message;
import ru.bikkul.compliment.telegram.bot.service.UserSettingService;
import ru.bikkul.compliment.telegram.bot.util.common.ReplyKeyboard;
import ru.bikkul.compliment.telegram.bot.util.command.Command;
import ru.bikkul.compliment.telegram.bot.util.quartz.TelegramScheduler;
import ru.bikkul.compliment.telegram.bot.util.handler.MessageSender;

@Slf4j
@Component
public class StopCommand implements Command {
    private final MessageSender messageSender;
    private final TelegramScheduler telegramScheduler;
    private final UserSettingService userSettingService;

    public StopCommand(MessageSender messageSender, TelegramScheduler telegramScheduler, UserSettingService userSettingService) {
        this.messageSender = messageSender;
        this.telegramScheduler = telegramScheduler;
        this.userSettingService = userSettingService;
    }

    @Override
    public void receivedCommand(Message message) {
        var chatId = message.getChatId();
        telegramScheduler.cronDelete(chatId);
        var text = "Работа бота остановлена, все настройки сброшены.";
        log.info("Работа бота для пользователя:{}, остановлена", chatId);
        userSettingService.setDefaultSetting(chatId);
        messageSender.sendMessage(chatId, text, ReplyKeyboard.setStopKeyboard());
    }
}
