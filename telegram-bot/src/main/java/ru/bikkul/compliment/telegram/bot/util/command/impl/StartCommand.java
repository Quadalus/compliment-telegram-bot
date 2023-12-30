package ru.bikkul.compliment.telegram.bot.util.command.impl;

import org.telegram.telegrambots.meta.api.objects.Message;

import static ru.bikkul.compliment.telegram.bot.util.BotConst.DEFAULT_CRON_EXPRESSION;
import static ru.bikkul.compliment.telegram.bot.util.BotConst.START_MESSAGE;

public class StartCommand {
    private void startSendingWishes(Message message) {
        var chatId = message.getChatId();
        saveStartUser(message);
        saveStartUserSettings(chatId);

        telegramScheduler.cronCreateJob(chatId, DEFAULT_CRON_EXPRESSION);
        log.info("start sending wishes to user id:{}", chatId);
    }

    private void saveStartUserSettings(Long chatId) {
//        if (userSettingsRepository.existsById(chatId)) {
//            var userSetting = getUserSettings(chatId);
//            userSetting.setIsScheduled(true);
//            userSettingsRepository.save(userSetting);
//        }
    }

    private void saveStartUser(Message message) {
        var chatId = message.getChatId();
    }

    private void startCommandReceived(long chatId) {
        sendMessage(chatId, START_MESSAGE);
    }
}
