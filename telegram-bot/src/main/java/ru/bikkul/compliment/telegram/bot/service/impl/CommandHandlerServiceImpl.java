package ru.bikkul.compliment.telegram.bot.service.impl;

import org.springframework.stereotype.Component;
import org.telegram.telegrambots.meta.api.objects.Update;
import ru.bikkul.compliment.telegram.bot.service.CommandHandlerService;
import ru.bikkul.compliment.telegram.bot.util.command.Command;
import ru.bikkul.compliment.telegram.bot.util.handler.RandomCallbackHandler;

import java.util.concurrent.ExecutorService;
import java.util.concurrent.Executors;

@Component
public class CommandHandlerServiceImpl implements CommandHandlerService  {
    private final ExecutorService executorService = Executors.newVirtualThreadPerTaskExecutor();
    private final Command helpCommand;
    private final Command startCommand;
    private final Command randomCommand;
    private final Command infoCommand;
    private final Command unknownCommand;
    private final Command timeCommand;
    private final Command stopCommand;
    private final Command textCommand;
    private final RandomCallbackHandler randomCallbackHandler;

    public CommandHandlerServiceImpl(Command helpCommand, Command startCommand, Command randomCommand, Command infoCommand, Command unknownCommand, Command timeCommand, Command stopCommand, Command textCommand, RandomCallbackHandler randomCallbackHandler) {
        this.helpCommand = helpCommand;
        this.startCommand = startCommand;
        this.randomCommand = randomCommand;
        this.infoCommand = infoCommand;
        this.unknownCommand = unknownCommand;
        this.timeCommand = timeCommand;
        this.stopCommand = stopCommand;
        this.textCommand = textCommand;
        this.randomCallbackHandler = randomCallbackHandler;
    }


    @Override
    public void receivedCommand(final Update update) {
        var message = update.getMessage();
        if (update.hasMessage() && message.hasText()) {
            var messageText = message.getText().toLowerCase();

            if (messageText.startsWith("/")) {
                switch (messageText) {
                    case "/start" -> executorService.execute(() -> startCommand.receivedCommand(message));
                    case "/random" -> executorService.execute(() -> randomCommand.receivedCommand(message));
                    case "/time" -> executorService.execute(() -> timeCommand.receivedCommand(message));
                    case "/help" -> executorService.execute(() -> helpCommand.receivedCommand(message));
                    case "/info" -> executorService.execute(() -> infoCommand.receivedCommand(message));
                    case "/stop" -> executorService.execute(() -> stopCommand.receivedCommand(message));
                    default -> executorService.execute(() -> unknownCommand.receivedCommand(message));
                }
            } else {
                executorService.execute(() -> textCommand.receivedCommand(message));
            }
        } else if (update.hasCallbackQuery()) {
            final var chatId = update.getCallbackQuery().getMessage().getChatId();
            executorService.execute(() -> randomCallbackHandler.receivedCallback(update, chatId));
        }
    }
}
