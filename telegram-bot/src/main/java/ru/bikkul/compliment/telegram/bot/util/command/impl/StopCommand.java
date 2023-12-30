package ru.bikkul.compliment.telegram.bot.util.command.impl;

public interface StopCommand {

    private void sendStopReceived(long chatId) {
        telegramScheduler.cronDelete(chatId);
        var text = "Работа бота остановлена, все настройки сброшены.";
        log.info("Работа бота для пользователя:{}, остановлена", chatId);
        setDefaultUserSettings(chatId);
        sendMessage(chatId, text);
    }
}
