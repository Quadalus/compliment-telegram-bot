package ru.bikkul.compliment.telegram.bot.util;

import org.springframework.stereotype.Component;
import org.telegram.telegrambots.meta.api.objects.Update;

@Component
public class CommandHandler {

    public void receivedCommand(Update update) {
        var message = update.getMessage();
        if (update.hasMessage() && message.hasText()) {
            var messageText = message.getText().toLowerCase();
            var chatId = message.getChatId();

            if (messageText.startsWith("/")) {
                switch (messageText) {
                    case "/start" -> startCommandReceived(chatId);
                    case "/random" -> sendRandomReceived(chatId);
                    case "/time" -> sendTimeReceived(chatId);
                    case "/help" -> sendHelpReceived(chatId);
                    case "/settings" -> infoCommandReciveid(chatId);
                    case "/stop" -> sendStopReceived(chatId);
                    default -> doNothing(chatId);
                }
            } else {
                receivedCallbackText(chatId, messageText);
            }
        } else if (update.hasCallbackQuery()) {
            var chatId = update.getCallbackQuery().getMessage().getChatId();
            receivedCallback(update, chatId);
        }
    }
}
