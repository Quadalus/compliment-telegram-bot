package ru.bikkul.compliment.telegram.bot.util.command.impl;

import lombok.extern.slf4j.Slf4j;
import org.springframework.stereotype.Component;
import org.telegram.telegrambots.meta.api.objects.Message;
import ru.bikkul.compliment.telegram.bot.service.UserService;
import ru.bikkul.compliment.telegram.bot.service.UserSettingService;
import ru.bikkul.compliment.telegram.bot.util.common.ReplyKeyboard;
import ru.bikkul.compliment.telegram.bot.util.command.Command;
import ru.bikkul.compliment.telegram.bot.util.quartz.TelegramScheduler;
import ru.bikkul.compliment.telegram.bot.util.handler.MessageHandler;

import static ru.bikkul.compliment.telegram.bot.util.common.BotConst.DEFAULT_CRON_EXPRESSION;
import static ru.bikkul.compliment.telegram.bot.util.common.BotConst.START_MESSAGE;

@Slf4j
@Component
public class StartCommand implements Command {
    private final MessageHandler messageHandler;
    private final TelegramScheduler telegramScheduler;
    private final UserSettingService userSettingService;
    private final UserService userService;

    public StartCommand(MessageHandler messageHandler, TelegramScheduler telegramScheduler, UserSettingService userSettingService, UserService userService) {
        this.messageHandler = messageHandler;
        this.telegramScheduler = telegramScheduler;
        this.userSettingService = userSettingService;
        this.userService = userService;
    }

    @Override
    public void receivedCommand(Message message) {
        var chatId = message.getChatId();
        sendStartMessage(chatId);
        startSendingWishes(message);
    }

    private void saveStartUserSettings(long chatId) {
        userSettingService.saveUserSetting(chatId);
    }

    private void saveStartUser(Message message) {
        userService.saveUser(message);
    }

    private void sendStartMessage(long chatId) {
        messageHandler.sendMessage(chatId, START_MESSAGE, ReplyKeyboard.setReplyKeyboard());
    }

    private void startSendingWishes(Message message) {
        var chatId = message.getChatId();
        saveStartUser(message);
        saveStartUserSettings(chatId);

        telegramScheduler.cronCreateJob(chatId, DEFAULT_CRON_EXPRESSION);
        log.info("start sending wishes to user id:{}", chatId);
    }
}
