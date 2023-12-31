package ru.bikkul.compliment.telegram.bot.util.handler;

import lombok.extern.slf4j.Slf4j;
import org.springframework.stereotype.Component;
import org.telegram.telegrambots.bots.TelegramLongPollingBot;
import org.telegram.telegrambots.meta.api.methods.send.SendMessage;
import org.telegram.telegrambots.meta.api.methods.send.SendPhoto;
import org.telegram.telegrambots.meta.api.methods.updatingmessages.EditMessageText;
import org.telegram.telegrambots.meta.api.objects.Update;
import org.telegram.telegrambots.meta.api.objects.replykeyboard.ReplyKeyboardMarkup;
import org.telegram.telegrambots.meta.exceptions.TelegramApiException;
import ru.bikkul.compliment.telegram.bot.config.BotConfig;

@Slf4j
@Component
public class MessageHandler extends TelegramLongPollingBot {
    private final BotConfig botConfig;

    public MessageHandler(BotConfig botConfig, BotConfig botConfig1) {
        super(botConfig.getBotToken());
        this.botConfig = botConfig1;
    }

    @Override
    public void onUpdateReceived(Update update) {
    }

    @Override
    public String getBotUsername() {
        return botConfig.getBotName();
    }

    public void sendMessage(long chatId) {
        String textToSend = "Извините, такой команды нет.";
        SendMessage msg = new SendMessage(String.valueOf(chatId), textToSend);
        try {
            this.execute(msg);
        } catch (TelegramApiException e) {
            log.error("error from sending message, error msg:{}", e.getMessage());
        }
    }

    public void sendMessage(long chatId, String textToSend) {
        SendMessage msg = new SendMessage(String.valueOf(chatId), textToSend);
        try {
            this.execute(msg);
        } catch (TelegramApiException e) {
            log.error("error from sending message, error msg:{}", e.getMessage());
        }
    }

    public void sendPhoto(SendPhoto photo) {
        try {
            this.execute(photo);
        } catch (TelegramApiException e) {
            log.error("error from sending message, error msg:{}", e.getMessage());
        }
    }

    public void sendEditMessage(long chatId, String text, int messageId) {
        var msg = new EditMessageText();
        msg.setChatId(chatId);
        msg.setText(text);
        msg.setMessageId(messageId);

        try {
            this.execute(msg);
        } catch (TelegramApiException e) {
            log.error("error from sending message, error msg:{}", e.getMessage());
        }
    }

    public void sendMessage(SendMessage message) {
        try {
            this.execute(message);
        } catch (TelegramApiException e) {
            log.error("error from sending message, error msg:{}", e.getMessage());
        }
    }

    public void sendMessage(long chatId, String text, ReplyKeyboardMarkup keyboard) {
        SendMessage message = new SendMessage();
        message.setChatId(chatId);
        message.setText(text);
        message.setReplyMarkup(keyboard);

        try {
            this.execute(message);
        } catch (TelegramApiException e) {
            log.error("error from sending message, error msg:{}", e.getMessage());
        }
    }
}
