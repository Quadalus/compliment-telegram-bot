package ru.bikkul.compliment.telegram.bot.service.impl;

import lombok.extern.slf4j.Slf4j;
import org.apache.commons.io.FileUtils;
import org.springframework.stereotype.Service;
import org.telegram.telegrambots.meta.api.methods.send.SendPhoto;
import org.telegram.telegrambots.meta.api.objects.InputFile;
import ru.bikkul.compliment.telegram.bot.client.GeneratePictureClient;
import ru.bikkul.compliment.telegram.bot.service.PictureService;
import ru.bikkul.compliment.telegram.bot.service.WishesService;
import ru.bikkul.compliment.telegram.bot.util.handler.MessageSender;

import java.io.File;
import java.io.IOException;
import java.nio.file.Files;
import java.nio.file.Path;
import java.util.Base64;
import java.util.concurrent.ThreadLocalRandom;

@Slf4j
@Service
public class PictureServiceImpl implements PictureService {
    private final GeneratePictureClient generatePictureClient;
    private final WishesService wishesService;
    private final MessageSender messageSender;

    public PictureServiceImpl(GeneratePictureClient generatePictureClient, WishesService wishesService, MessageSender messageSender) {
        this.generatePictureClient = generatePictureClient;
        this.wishesService = wishesService;
        this.messageSender = messageSender;
    }

    @Override
    public void sendRandomPicture(long chatId) {
        var url = "telegram-bot/src/main/resources/img/%d-%d.png"
                .formatted(chatId, ThreadLocalRandom.current().nextInt(Integer.MAX_VALUE));
        var picture = getPicture(chatId, url);
        var caption = wishesService.getRandomWish();
        var photo = new SendPhoto();
        photo.setChatId(chatId);
        photo.setCaption(caption);
        photo.setPhoto(new InputFile(picture));
        messageSender.sendPhoto(photo);
        log.info("Пожелание с добрым утром отправлено пользователю:{}", chatId);
        //deleteFile(url);
    }

    @Override
    public void sendRandomPictureWithoutCaption(long chatId) {
        var url = "telegram-bot/src/main/resources/img/%d-%d.png"
                .formatted(chatId, ThreadLocalRandom.current().nextInt(Integer.MAX_VALUE));
        var picture = getPicture(chatId, url);
        var photo = new SendPhoto();
        photo.setChatId(chatId);
        photo.setPhoto(new InputFile(picture));
        messageSender.sendPhoto(photo);
        log.info("Случайная картинка отправлена пользователю:{}", chatId);
//      deleteFile(url)
    }

    private File getPicture(long chatId, String url) {
        var resutPictureDto = generatePictureClient.generatePicture(chatId);

        var images = resutPictureDto.getImages();

        if (!(images == null) && !images.isEmpty()) {
            var s = images.getFirst();
            byte[] decodedBytes = Base64.getDecoder().decode(s);
            try {
                FileUtils.writeByteArrayToFile(new File(url), decodedBytes);
            } catch (IOException e) {
                log.error("error");
            }
        }
        return FileUtils.getFile(url);
    }

    private static void deleteFile(String url) {
        try {
            Files.deleteIfExists(Path.of(url));
        } catch (IOException e) {
            log.error("file with url:{}, not exists", url);
        }
    }
}
