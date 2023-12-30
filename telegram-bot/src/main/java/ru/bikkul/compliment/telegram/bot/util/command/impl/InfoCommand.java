package ru.bikkul.compliment.telegram.bot.util.command.impl;

public class InfoCommand {
    private void infoCommandReciveid(long chatId) {
        var time = telegramScheduler.getCronTime(chatId);

        var source = "pozdravok.com";

        var text = """
                Ваши настройки:
                 - Время уведомлений: %s
                 - Источник утренних желаний: %s
                """.formatted(time, source);
        sendMessage(chatId, text);
    }
}
