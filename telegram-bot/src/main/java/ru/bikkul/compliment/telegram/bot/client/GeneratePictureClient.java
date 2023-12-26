package ru.bikkul.compliment.telegram.bot.client;

import ru.bikkul.compliment.telegram.bot.dto.ResultPictureResponseDto;

public interface GeneratePictureClient {
    ResultPictureResponseDto generatePicture(long chatId);
}
