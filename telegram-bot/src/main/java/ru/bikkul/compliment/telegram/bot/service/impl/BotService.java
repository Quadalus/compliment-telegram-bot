package ru.bikkul.compliment.telegram.bot.service.impl;

import lombok.extern.slf4j.Slf4j;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.telegram.telegrambots.bots.TelegramLongPollingBot;
import org.telegram.telegrambots.meta.api.methods.commands.SetMyCommands;
import org.telegram.telegrambots.meta.api.objects.Update;
import org.telegram.telegrambots.meta.api.objects.commands.scope.BotCommandScopeDefault;
import org.telegram.telegrambots.meta.exceptions.TelegramApiException;
import ru.bikkul.compliment.telegram.bot.client.GeneratePictureClient;
import ru.bikkul.compliment.telegram.bot.config.BotConfig;
import ru.bikkul.compliment.telegram.bot.util.CommandHandler;

import static ru.bikkul.compliment.telegram.bot.util.BotConst.LIST_OF_COMMAND;

@Slf4j
@Service
public class BotService extends TelegramLongPollingBot {
    private final BotConfig botConfig;
    private final CommandHandler commandHandler;


    @Autowired
    public BotService(BotConfig botConfig, GeneratePictureClient generatePictureClient, CommandHandler commandHandler) {
        super(botConfig.getBotToken());
        this.botConfig = botConfig;
        this.commandHandler = commandHandler;
    }

    public void init() throws TelegramApiException {
        execute(new SetMyCommands(LIST_OF_COMMAND, new BotCommandScopeDefault(), null));
    }

    @Override
    public void onUpdateReceived(Update update) {
        commandHandler.receivedCommand(update);
    }

    @Override
    public String getBotUsername() {
        return botConfig.getBotName();
    }

    @Override
    public void onRegister() {
        super.onRegister();
    }
}
