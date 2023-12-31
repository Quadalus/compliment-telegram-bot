package ru.bikkul.compliment.telegram.bot.service;

public interface PictureService {
    void sendRandomPicture(long chatId);

    void sendRandomPictureWithoutCaption(long chatId);
}
