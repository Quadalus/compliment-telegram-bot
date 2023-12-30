package ru.bikkul.compliment.telegram.bot.util.command.impl;

public class UnknownCommand {
    private void doNothing(Long chatId) {
        sendMessage(chatId);
    }
}
