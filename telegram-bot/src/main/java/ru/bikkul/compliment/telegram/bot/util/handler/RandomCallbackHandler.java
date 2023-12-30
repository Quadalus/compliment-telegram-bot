package ru.bikkul.compliment.telegram.bot.util.handler;

import lombok.extern.slf4j.Slf4j;
import org.springframework.stereotype.Component;
import org.telegram.telegrambots.meta.api.objects.Update;
import ru.bikkul.compliment.telegram.bot.service.impl.PictureServiceImpl;
import ru.bikkul.compliment.telegram.bot.service.impl.WishesServiceImpl;

import static ru.bikkul.compliment.telegram.bot.util.enums.InlineButton.PICTURE_BUTTON;
import static ru.bikkul.compliment.telegram.bot.util.enums.InlineButton.TEXT_BUTTON;

@Slf4j
@Component
public class RandomCallbackHandler {
    private final PictureServiceImpl pictureService;
    private final WishesServiceImpl wishesService;
    private final MessageHandler messageHandler;

    public RandomCallbackHandler(PictureServiceImpl pictureService, WishesServiceImpl wishesService, MessageHandler messageHandler) {
        this.pictureService = pictureService;
        this.wishesService = wishesService;
        this.messageHandler = messageHandler;
    }

    public void receivedCallback(Update update, long chatId) {
        String text;
        var callbackData = update.getCallbackQuery().getData();
        var messageId = update.getCallbackQuery().getMessage().getMessageId();

        if (callbackData.equals(TEXT_BUTTON.name())) {
            text = "Держите случайное пожелание с добрым утром:";
            messageHandler.sendEditMessage(chatId, text, messageId);
            wishesService.sendRandomWish(chatId);
        } else if (callbackData.equals(PICTURE_BUTTON.name())) {
            text = "Запрос на генерацию картинки получен, ожидайте.";
            messageHandler.sendEditMessage(chatId, text, messageId);
            pictureService.sendRandomPictureWithoutCaption(chatId);
        }
    }
}
