package ru.bikkul.compliment.telegram.bot.util.command.impl;

import org.springframework.stereotype.Component;
import org.telegram.telegrambots.meta.api.objects.Message;
import ru.bikkul.compliment.telegram.bot.service.UserSettingService;
import ru.bikkul.compliment.telegram.bot.util.command.Command;
import ru.bikkul.compliment.telegram.bot.util.common.UserTextCommands;
import ru.bikkul.compliment.telegram.bot.util.handler.MessageSender;
import ru.bikkul.compliment.telegram.bot.util.quartz.TelegramScheduler;

import java.util.regex.Matcher;
import java.util.regex.Pattern;

import static ru.bikkul.compliment.telegram.bot.util.common.BotConst.*;

@Component
public class TextCommand implements Command {
    private final MessageSender messageSender;
    private final UserSettingService userSettingService;
    private final TelegramScheduler telegramScheduler;
    private final Command unknownCommand;
    private final Command randomCommand;
    private final Command timeCommand;
    private final Command startCommand;

    public TextCommand(MessageSender messageSender, UserSettingService userSettingService, TelegramScheduler telegramScheduler, Command unknownCommand, Command randomCommand, Command timeCommand, Command startCommand) {
        this.messageSender = messageSender;
        this.userSettingService = userSettingService;
        this.telegramScheduler = telegramScheduler;
        this.unknownCommand = unknownCommand;
        this.randomCommand = randomCommand;
        this.timeCommand = timeCommand;
        this.startCommand = startCommand;
    }

    @Override
    public void receivedCommand(Message message) {
        var chatId = message.getChatId();
        String text = UserTextCommands.peekCoomand(chatId);
        var command = text == null ? message.getText() : text;
        var messageText = message.getText();

        switch (command) {
            case "/time" -> updateTime(chatId, messageText);
            case TIME_ROW_TEXT -> timeCommand.receivedCommand(message);
            case START_ROW_TEXT -> {
                startCommand.receivedCommand(message);
                UserTextCommands.removeCommand(chatId);
            }
            case RANDOM_ROW_TEXT -> {
                randomCommand.receivedCommand(message);
                UserTextCommands.removeCommand(chatId);
            }
            case null, default -> unknownCommand.receivedCommand(message);
        }
    }

    private void updateTime(long chatId, String messageText) {
        Pattern pattern = Pattern.compile("([0-9]|0[0-9]|1[0-9]|2[0-3])[:,./\\\\\\- ][0-5][0-9]");
        Matcher matcher = pattern.matcher(messageText);

        if (matcher.find()) {
            updateUserTime(chatId, messageText);
        } else {
            messageSender.sendMessage(chatId, "Неверный формат времени, пример формата: 12:42");
        }
    }

    private void updateUserTime(long chatId, String text) {
        var userSetting = userSettingService.getUserSettings(chatId);
        var times = text.split("[:,./\\\\\\- ]");
        var hour = times[0];
        var min = times[1];
        var newCronTime = "0 %s %s ? * * *".formatted(min, hour);
        userSetting.setCronTime(newCronTime);
        telegramScheduler.cronUpdate(chatId, newCronTime);
        String timeText = "Установлено новое время:" + text;
        UserTextCommands.removeCommand(chatId);
        messageSender.sendMessage(chatId, timeText);
    }
}
