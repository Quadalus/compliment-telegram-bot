package ru.bikkul.compliment.telegram.bot.service.impl;

import lombok.extern.slf4j.Slf4j;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.telegram.telegrambots.bots.TelegramLongPollingBot;
import org.telegram.telegrambots.meta.api.methods.commands.SetMyCommands;
import org.telegram.telegrambots.meta.api.methods.send.SendMessage;
import org.telegram.telegrambots.meta.api.objects.Update;
import org.telegram.telegrambots.meta.api.objects.commands.BotCommand;
import org.telegram.telegrambots.meta.api.objects.commands.scope.BotCommandScopeDefault;
import org.telegram.telegrambots.meta.exceptions.TelegramApiException;
import ru.bikkul.compliment.telegram.bot.config.BotConfig;
import ru.bikkul.compliment.telegram.bot.repository.UserRepository;

import java.util.ArrayList;
import java.util.List;

@Slf4j
@Service
public class BotServiceImpl extends TelegramLongPollingBot {
    private final BotConfig botConfig;
    private final UserRepository userRepository;
    private final List<BotCommand> botCommands = new ArrayList<>();

    {
        botCommands.add(new BotCommand("/start", "Запуск приложения."));
        botCommands.add(new BotCommand("/random", "Получить рандомное пожелание на утро."));
        botCommands.add(new BotCommand("/settings", "Настройка получений пожеланий."));
        botCommands.add(new BotCommand("/stop", "Остановка приложения."));
        try {
            execute(new SetMyCommands(botCommands, new BotCommandScopeDefault(), null));
        } catch (TelegramApiException e) {

        }
    }

    @Autowired
    public BotServiceImpl(BotConfig botConfig, UserRepository userRepository) {
        super(botConfig.getBotToken());
        this.botConfig = botConfig;
        this.userRepository = userRepository;
    }

    @Override
    public void onUpdateReceived(Update update) {
        if (update.hasMessage() && update.getMessage().hasText()) {
            String messageText = update.getMessage().getText().toLowerCase();
            long chatId = update.getMessage().getChatId();
            String userName = update.getMessage().getChat().getUserName();
            String userFirstName = update.getMessage().getChat().getFirstName();

            switch (messageText) {
                case "/start" -> startCommandReceived(chatId, userName);
//                case "привет" -> startCommandReceived(chatId, userFirstName);
                default -> doNothing(chatId);
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

    private void sendMessage(long chatId) {
        String textToSend = "Извините, такой комманды нет.";

        SendMessage msg = new SendMessage(String.valueOf(chatId), textToSend);

        try {
            execute(msg);
        } catch (TelegramApiException e) {
            log.error("error from sending message, error msg:{}", e.getMessage());
        }
    }

    private void doNothing(Long chatId) {
        sendMessage(chatId);
    }
}
