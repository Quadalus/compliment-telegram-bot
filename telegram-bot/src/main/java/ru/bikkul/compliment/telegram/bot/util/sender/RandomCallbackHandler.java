package ru.bikkul.compliment.telegram.bot.util.sender;

import lombok.extern.slf4j.Slf4j;
import org.telegram.telegrambots.meta.api.objects.Update;

import static ru.bikkul.compliment.telegram.bot.util.enums.InlineButton.PICTURE_BUTTON;
import static ru.bikkul.compliment.telegram.bot.util.enums.InlineButton.TEXT_BUTTON;

@Slf4j
public class RandomCallbackHandler {
    public void receivedCallback(Update update, long chatId) {
        String text;
        var callbackData = update.getCallbackQuery().getData();
        var messageId = update.getCallbackQuery().getMessage().getMessageId();

        if (callbackData.equals(TEXT_BUTTON.name())) {
            text = "Держите случайное пожелание с добрым утром:";
            sendEditMessage(chatId, text, messageId);
            sendRandomWish(chatId);
        } else if (callbackData.equals(PICTURE_BUTTON.name())) {
            text = "Запрос на генерацию картинки получен, ожидайте.";
            sendEditMessage(chatId, text, messageId);
            sendRandomPictureWithoutCaption(chatId);
        }
    }
}
