package ru.bikkul.compliment.telegram.bot.util.command.impl;

public class HelpComand {
    private void sendHelpReceived(long chatId) {
        sendMessage(chatId, helpMessage);
    }
}
