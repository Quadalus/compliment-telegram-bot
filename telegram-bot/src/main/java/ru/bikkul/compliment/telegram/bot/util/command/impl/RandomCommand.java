package ru.bikkul.compliment.telegram.bot.util.command.impl;

import org.telegram.telegrambots.meta.api.methods.send.SendMessage;
import org.telegram.telegrambots.meta.api.objects.replykeyboard.InlineKeyboardMarkup;
import org.telegram.telegrambots.meta.api.objects.replykeyboard.buttons.InlineKeyboardButton;

import java.util.ArrayList;
import java.util.List;

import static ru.bikkul.compliment.telegram.bot.util.enums.InlineButton.PICTURE_BUTTON;
import static ru.bikkul.compliment.telegram.bot.util.enums.InlineButton.TEXT_BUTTON;

public class RandomCommand {
    public void sendRandomReceived(long chatId) {
        SendMessage message = new SendMessage();
        message.setChatId(String.valueOf(chatId));
        message.setText("Что вы хотите получить?");

        InlineKeyboardMarkup markupInLine = new InlineKeyboardMarkup();
        List<List<InlineKeyboardButton>> rowsInLine = new ArrayList<>();
        var rowInLine = getInlineKeyboardButtons();
        rowsInLine.add(rowInLine);
        markupInLine.setKeyboard(rowsInLine);
        message.setReplyMarkup(markupInLine);

        sendMessage(message);
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
