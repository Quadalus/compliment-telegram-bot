package ru.bikkul.compliment.telegram.bot.util.common;

import org.telegram.telegrambots.meta.api.objects.replykeyboard.ReplyKeyboardMarkup;
import org.telegram.telegrambots.meta.api.objects.replykeyboard.buttons.KeyboardRow;

import java.util.List;

import static ru.bikkul.compliment.telegram.bot.util.common.BotConst.*;

public class ReplyKeyboard {
    private ReplyKeyboard() {
    }

    public static ReplyKeyboardMarkup setReplyKeyboard() {
        var keyboard = new ReplyKeyboardMarkup();
        var randomRow = new KeyboardRow();
        var timeRow = new KeyboardRow();

        randomRow.add(RANDOM_ROW_TEXT);
        timeRow.add(TIME_ROW_TEXT);
        keyboard.setKeyboard(List.of(randomRow, timeRow));
        keyboard.setResizeKeyboard(true);
        return keyboard;
    }

    public static ReplyKeyboardMarkup setStopKeyboard() {
        var keyboard = new ReplyKeyboardMarkup();
        var stopRow = new KeyboardRow();
        stopRow.add(STOP_ROW_TEXT);
        keyboard.setKeyboard(List.of(stopRow));
        keyboard.setResizeKeyboard(true);
        return keyboard;
    }
}
