package ru.bikkul.compliment.telegram.bot.util.sender;

import lombok.extern.slf4j.Slf4j;
import org.springframework.stereotype.Component;
import org.telegram.telegrambots.meta.api.methods.send.SendMessage;
import org.telegram.telegrambots.meta.api.methods.send.SendPhoto;
import org.telegram.telegrambots.meta.api.methods.updatingmessages.EditMessageText;
import ru.bikkul.compliment.telegram.bot.util.enums.MessageType;

import java.util.concurrent.ExecutorService;
import java.util.concurrent.Executors;

@Slf4j
@Component
public class Sender {
    private final ExecutorService executorService = Executors.newVirtualThreadPerTaskExecutor();

    private void send(Object object) {
        var messageType = getMessageType(object);

        switch (messageType) {
            case MESSAGE -> executorService.execute(() -> System.out.println());
            case EDIT_MESSAGE -> executorService.execute(() -> System.out.println());
            case PHOTO -> executorService.execute(() -> System.out.println());
            case UNKNOWN -> log.error("Неизвестный тип сообщения");
        }
    }

    private MessageType getMessageType(Object object) {
        return switch (object) {
            case SendMessage message -> MessageType.MESSAGE;
            case EditMessageText editMessage -> MessageType.EDIT_MESSAGE;
            case SendPhoto photo -> MessageType.PHOTO;
            default -> MessageType.UNKNOWN;
        };
    }
}
