package ru.bikkul.compliment.telegram.bot.util.command.impl;

import org.springframework.stereotype.Component;
import org.telegram.telegrambots.meta.api.methods.send.SendMessage;
import org.telegram.telegrambots.meta.api.objects.Message;
import org.telegram.telegrambots.meta.api.objects.replykeyboard.InlineKeyboardMarkup;
import org.telegram.telegrambots.meta.api.objects.replykeyboard.buttons.InlineKeyboardButton;
import ru.bikkul.compliment.telegram.bot.util.command.Command;
import ru.bikkul.compliment.telegram.bot.util.handler.MessageSender;

import java.util.ArrayList;
import java.util.List;

import static ru.bikkul.compliment.telegram.bot.util.enums.InlineButton.PICTURE_BUTTON;
import static ru.bikkul.compliment.telegram.bot.util.enums.InlineButton.TEXT_BUTTON;

@Component
public class RandomCommand implements Command {
    private final MessageSender messageSender;

    public RandomCommand(MessageSender messageSender) {
        this.messageSender = messageSender;
    }

    @Override
    public void receivedCommand(Message message) {
        Long chatId = message.getChatId();
        SendMessage receivedMessage = new SendMessage();
        receivedMessage.setChatId(String.valueOf(receivedMessage));
        receivedMessage.setText("Что вы хотите получить?");

        InlineKeyboardMarkup markupInLine = new InlineKeyboardMarkup();
        List<List<InlineKeyboardButton>> rowsInLine = new ArrayList<>();
        var rowInLine = getInlineKeyboardButtons();
        rowsInLine.add(rowInLine);
        markupInLine.setKeyboard(rowsInLine);
        receivedMessage.setReplyMarkup(markupInLine);
        receivedMessage.setChatId(chatId);
        messageSender.sendMessage(receivedMessage);
    }

    private static List<InlineKeyboardButton> getInlineKeyboardButtons() {
        List<InlineKeyboardButton> rowInLine = new ArrayList<>();
        var randomTextButton = new InlineKeyboardButton();

        randomTextButton.setText("Случайное пожелание.");
        randomTextButton.setCallbackData(TEXT_BUTTON.name());

        var randomPictureButton = new InlineKeyboardButton();

        randomPictureButton.setText("Случайную картинку.");
        randomPictureButton.setCallbackData(PICTURE_BUTTON.name());

        rowInLine.add(randomTextButton);
        rowInLine.add(randomPictureButton);
        return rowInLine;
    }
}
