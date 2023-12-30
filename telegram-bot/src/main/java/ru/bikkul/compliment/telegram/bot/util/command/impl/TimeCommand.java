package ru.bikkul.compliment.telegram.bot.util.command.impl;

import java.util.Objects;
import java.util.regex.Matcher;
import java.util.regex.Pattern;

public class TimeCommand {
    private void sendTimeReceived(long chatId) {
        var text = "Введите новое время в формате часы:минуты";
        commandCallbacks.add("/time");
        sendMessage(chatId, text);
    }

    private void updateTime(long chatId, String messageText) {
        Pattern pattern = Pattern.compile("([0-9]|0[0-9]|1[0-9]|2[0-3]):[0-5][0-9]");
        Matcher matcher = pattern.matcher(messageText);

        if (matcher.find()) {
            updateUserTime(chatId, messageText);
        } else {
            sendMessage(chatId, "Неверный формат времени, пример формата: 12:42");
        }
    }

    private void updateUserTime(long chatId, String text) {
        var userSetting = getUserSetting(chatId);
        var times = text.split(":");
        var hour = times[0];
        var min = times[1];
        var newCronTime = "0 %s %s ? * * *".formatted(min, hour);
        userSetting.setCronTime(newCronTime);
        telegramScheduler.cronUpdate(chatId, newCronTime);
        sendMessage(chatId, "Установлено новое время:" + text);
        commandCallbacks.remove();
    }

    private void receivedCallbackText(long chatId, String messageText) {
        var command = commandCallbacks.peek();

        if (Objects.requireNonNull(command).equals("/time")) {
            updateTime(chatId, messageText);
        } else {
            doNothing(chatId);
        }
    }
}
