package ru.bikkul.compliment.telegram.bot.service.impl;

import lombok.extern.slf4j.Slf4j;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.telegram.telegrambots.bots.TelegramLongPollingBot;
import org.telegram.telegrambots.meta.api.methods.send.SendMessage;
import org.telegram.telegrambots.meta.api.objects.Update;
import org.telegram.telegrambots.meta.exceptions.TelegramApiException;
import ru.bikkul.compliment.telegram.bot.config.BotConfig;

@Slf4j
@Service
public class BotServiceImpl extends TelegramLongPollingBot {
    private final BotConfig botConfig;

    @Autowired
    public BotServiceImpl(BotConfig botConfig) {
        super(botConfig.getBotToken());
        this.botConfig= botConfig;
    }

    @Override
    public void onUpdateReceived(Update update) {
        if (update.hasMessage() && update.getMessage().hasText()) {
            String messageText = update.getMessage().getText();
            long chatId = update.getMessage().getChatId();
            String userName = update.getMessage().getChat().getUserName();

            switch (messageText) {
                case "/start" -> startCommandReceived(chatId, userName);
                default -> doNothing();
            }
        }
    }

    @Override
    public String getBotUsername() {
        return botConfig.getBotName();
    }



    @Override
    public void onRegister() {
        super.onRegister();
    }

    private void startCommandReceived(long chartId, String name) {
        String answer = String.format(("Привет, %s"), name);
        sendMessage(chartId, answer);
    }

    private void sendMessage(long chatId, String textToSend) {
        SendMessage msg = new SendMessage(String.valueOf(chatId), textToSend);

        try {
            execute(msg);
        } catch (TelegramApiException e) {
            log.error("error from sending message, error msg:{}", e.getMessage());
        }
    }

    private void doNothing() {
    }
}
