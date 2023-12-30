package ru.bikkul.compliment.telegram.bot.util.handler;

import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Component;
import org.telegram.telegrambots.meta.api.objects.Update;
import ru.bikkul.compliment.telegram.bot.service.Sender;
import ru.bikkul.compliment.telegram.bot.util.command.impl.*;

import java.util.concurrent.ExecutorService;
import java.util.concurrent.Executors;

@Component
@RequiredArgsConstructor
public class CommandHandler {
    private final ExecutorService executorService = Executors.newVirtualThreadPerTaskExecutor();
    private final StartCommand startCommand;
    private final HelpCommand helpCommand;
    private final RandomCommand randomCommand;
    private final InfoCommand infoCommand;
    private final UnknownCommand unknownCommand;
    private final TimeCommand timeCommand;
    private final StopCommand stopCommand;
    private final TextCommandHandler textCommandHandler;
    private final RandomCallbackHandler randomCallbackHandler;

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
                executorService.execute(() -> executorService.execute(() -> textCommandHandler.receivedCommand(message)));
            }
        } else if (update.hasCallbackQuery()) {
            final var chatId = update.getCallbackQuery().getMessage().getChatId();
            executorService.execute(() -> randomCallbackHandler.receivedCallback(update, chatId));
        }
    }
}
