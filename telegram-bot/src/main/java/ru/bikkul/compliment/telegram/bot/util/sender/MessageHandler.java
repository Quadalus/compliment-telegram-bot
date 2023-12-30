package ru.bikkul.compliment.telegram.bot.util.sender;

import lombok.extern.slf4j.Slf4j;
import org.springframework.stereotype.Component;
import org.telegram.telegrambots.meta.api.methods.send.SendMessage;
import org.telegram.telegrambots.meta.api.methods.send.SendPhoto;
import org.telegram.telegrambots.meta.api.methods.updatingmessages.EditMessageText;
import org.telegram.telegrambots.meta.exceptions.TelegramApiException;
import ru.bikkul.compliment.telegram.bot.service.impl.BotService;

@Slf4j
@Component
public class MessageHandler {
    private final BotService botService;


    public MessageHandler(BotService botService) {
        this.botService = botService;
    }

    public void sendMessage(long chatId) {
        String textToSend = "Извините, такой команды нет.";
        SendMessage msg = new SendMessage(String.valueOf(chatId), textToSend);
        try {
            botService.execute(msg);
        } catch (TelegramApiException e) {
            log.error("error from sending message, error msg:{}", e.getMessage());
        }
    }

    private void sendMessage(long chatId, String textToSend) {
        SendMessage msg = new SendMessage(String.valueOf(chatId), textToSend);
        try {
            botService.execute(msg);
        } catch (TelegramApiException e) {
            log.error("error from sending message, error msg:{}", e.getMessage());
        }
    }

    private void sendPhoto(SendPhoto photo) {
        try {
            botService.execute(photo);
        } catch (TelegramApiException e) {
            log.error("error from sending message, error msg:{}", e.getMessage());
        }
    }

    private void sendEditMessage(long chatId, String text, int messageId) {
        var msg = new EditMessageText();
        msg.setChatId(chatId);
        msg.setText(text);
        msg.setMessageId(messageId);

        try {
            botService.execute(msg);
        } catch (TelegramApiException e) {
            log.error("error from sending message, error msg:{}", e.getMessage());
        }
    }
}
