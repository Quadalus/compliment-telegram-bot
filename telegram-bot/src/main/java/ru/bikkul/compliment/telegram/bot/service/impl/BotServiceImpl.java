package ru.bikkul.compliment.telegram.bot.service.impl;

import lombok.extern.slf4j.Slf4j;
import org.apache.commons.io.FileUtils;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.scheduling.annotation.Async;
import org.springframework.stereotype.Service;
import org.telegram.telegrambots.bots.TelegramLongPollingBot;
import org.telegram.telegrambots.meta.api.methods.commands.SetMyCommands;
import org.telegram.telegrambots.meta.api.methods.send.SendMessage;
import org.telegram.telegrambots.meta.api.methods.send.SendPhoto;
import org.telegram.telegrambots.meta.api.objects.InputFile;
import org.telegram.telegrambots.meta.api.objects.Message;
import org.telegram.telegrambots.meta.api.objects.Update;
import org.telegram.telegrambots.meta.api.objects.commands.BotCommand;
import org.telegram.telegrambots.meta.api.objects.commands.scope.BotCommandScopeDefault;
import org.telegram.telegrambots.meta.exceptions.TelegramApiException;
import ru.bikkul.compliment.telegram.bot.client.GeneratePictureClient;
import ru.bikkul.compliment.telegram.bot.config.BotConfig;
import ru.bikkul.compliment.telegram.bot.model.GoodMorning;
import ru.bikkul.compliment.telegram.bot.repository.GoodMorningRepository;
import ru.bikkul.compliment.telegram.bot.service.BotService;
import ru.bikkul.compliment.telegram.bot.util.quartz.TelegramScheduler;

import java.io.File;
import java.io.IOException;
import java.nio.file.Files;
import java.nio.file.Path;
import java.util.*;
import java.util.concurrent.ThreadLocalRandom;
import java.util.regex.Matcher;
import java.util.regex.Pattern;

@Slf4j
@Service
public class BotServiceImpl extends TelegramLongPollingBot implements BotService {
    private final TelegramScheduler telegramScheduler;
    private final BotConfig botConfig;
    private final GoodMorningRepository goodMorningRepository;
    private final List<BotCommand> botCommands = new ArrayList<>();
    private final Queue<String> commandCallbacks = new ArrayDeque<>();
    private final GeneratePictureClient generatePictureClient;
    private List<GoodMorning> wishes;

    {
        botCommands.add(new BotCommand("/start", "Запуск приложения."));
        botCommands.add(new BotCommand("/random", "Получить случайное пожелание на утро."));
        botCommands.add(new BotCommand("/help", "Получить подсказки."));
        botCommands.add(new BotCommand("/settings", "Получить текущие настройки."));
        botCommands.add(new BotCommand("/stop", "Остановка приложения."));
        try {
            execute(new SetMyCommands(botCommands, new BotCommandScopeDefault(), null));
        } catch (TelegramApiException e) {
            throw new RuntimeException(e);
        }
    }

    @Autowired
    public BotServiceImpl(TelegramScheduler telegramScheduler, BotConfig botConfig, GoodMorningRepository goodMorningRepository, GeneratePictureClient generatePictureClient) {
        super(botConfig.getBotToken());
        this.telegramScheduler = telegramScheduler;
        this.botConfig = botConfig;
        this.goodMorningRepository = goodMorningRepository;
        this.generatePictureClient = generatePictureClient;
        fillPozdravokWishes();
    }

    @Override
    public void onUpdateReceived(Update update) {
        var message = update.getMessage();
        if (update.hasMessage() && message.hasText()) {
            String messageText = message.getText().toLowerCase();
            long chatId = message.getChatId();

            if (!commandCallbacks.isEmpty()) {
                receivedCallback(chatId, messageText);
            } else {
                switch (messageText) {
                    case "/start" -> {
                        startSendingWishes(message);
                        startCommandReceived(chatId);
                    }
                    case "/random" -> sendRandomWish(chatId);
                    case "/time" -> sendTimeReceived(chatId);
                    case "/help" -> sendHelpReceived(chatId);
                    case "/settings" -> settingsCommandReceived(chatId);
                    case "/stop" -> sendStopReceivec(chatId);
                    default -> doNothing(chatId);
                }
            }
        }
    }

    private void settingsCommandReceived(long chatId) {
        var time = telegramScheduler.getCronTime(chatId);

        var source = "pozdravok.com";

        var text = """
                Ваши настройки:
                 - Время уведомлений: %s
                 - Источник утренних желаний: %s
                """.formatted(time, source);
        sendMessage(chatId, text);
    }

    @Override
    @Async
    public void sendRandomWish(long chatId) {
        var wish = getRandomWish();
        sendMessage(chatId, wish);
    }

    private void receivedCallback(long chatId, String messageText) {
        var command = commandCallbacks.peek();

        if (Objects.requireNonNull(command).equals("/time")) {
            updateTime(chatId, messageText);
        } else {
            doNothing(chatId);
        }
    }

    private void updateTime(long chatId, String messageText) {
        Pattern pattern = Pattern.compile("([0-9]|0[0-9]|1[0-9]|2[0-3]):[0-5][0-9]");
        Matcher matcher = pattern.matcher(messageText);

        if (matcher.find()) {
            updateUserTime(chatId, messageText);
        } else {
            sendMessage(chatId, "Неверный формат времени, пример формата: 12:42");
        }
    }

    private void updateUserTime(long chatId, String text) {
        var times = text.split(":");
        var hour = times[0];
        var min = times[1];
        telegramScheduler.cronUpdate(chatId, "0 %s %s ? * * *".formatted(min, hour));
        sendMessage(chatId, "Установлено новое время:" + text);
        commandCallbacks.remove();
    }

    @Override
    public String getBotUsername() {
        return botConfig.getBotName();
    }

    @Override
    public void onRegister() {
        super.onRegister();
    }

    @Override
    @Async
    public void sendRandomPicture(long chatId) {
        var url = "telegram-bot/src/main/resources/img/%d-%d.png"
                .formatted(chatId, ThreadLocalRandom.current().nextInt(Integer.MAX_VALUE));
        var picture = getPicture(chatId, url);
        var caption = getRandomWish();
        var photo = new SendPhoto();
        photo.setChatId(chatId);
        photo.setCaption(caption);
        photo.setPhoto(new InputFile(picture));
        sendPhoto(photo);
        log.info("Пожелание с добрым утром отправлено пользователю:{}", chatId);
        deleteFile(url);
    }

    private static void deleteFile(String url) {
        try {
            Files.deleteIfExists(Path.of(url));
        } catch (IOException e) {
            log.error("file with url:{}, not exists", url);
        }
    }

    private File getPicture(long chatId, String url) {
        var resutPictureDto = generatePictureClient.generatePicture(chatId);

        var images = resutPictureDto.getImages();

        if (!(images == null) && !images.isEmpty()) {
            var s = images.get(0);
            byte[] decodedBytes = Base64.getDecoder().decode(s);
            try {
                FileUtils.writeByteArrayToFile(new File(url), decodedBytes);
            } catch (IOException e) {
                log.error("error");
            }
        }
        return FileUtils.getFile(url);
    }

    private void startSendingWishes(Message message) {
        var chatId = message.getChatId();
        String DEFAULT_CRON_EXPRESSION = "0 0 8 ? * * *";
        telegramScheduler.cronCreateJob(chatId, DEFAULT_CRON_EXPRESSION);
        log.info("start sending wishes to user id:{}", chatId);
    }

    private void startCommandReceived(long chatId) {
        String answer = """
                Привет, Я Good Morning Bot 🤖! Я умею:
                               
                 • генерировать картинку каждое утро 🖼
                 • присылать пожелания с добрым утром
                 • присылать интересные факты
                                 
                Бот по умолчанию присылает пожелания с добрым утром в 08:00. Для смены времени  воспользуетесь командой /time
                                 
                Для подробной информации  воспользуетесь командой /help
                 """;
        sendMessage(chatId, answer);
    }

    private void sendHelpReceived(long chatId) {
        String helpMessage = """
                Доступные команды:
                /start - начать работу с ботом
                /help - помощь по боту
                /settings - посмотреть текущие настройки
                /random - получить случайную картинку или пожелание
                /time - установить новое время для доставки пожеланий
                """;
        sendMessage(chatId, helpMessage);
    }

    private void sendTimeReceived(long chatId) {
        var text = "Введите новое время в формате часы:минуты";
        commandCallbacks.add("/time");
        sendMessage(chatId, text);
    }

    private void sendStopReceivec(long chatId) {
        telegramScheduler.cronDelete(chatId);
        var text = "Работа бота остановлена, все настройки сброшены.";
        log.info("Работа бота для пользователя:{}, остановлена", chatId);
        sendMessage(chatId, text);
    }

    private void sendMessage(long chatId, String textToSend) {
        SendMessage msg = new SendMessage(String.valueOf(chatId), textToSend);
        try {
            execute(msg);
        } catch (TelegramApiException e) {
            log.error("error from sending message, error msg:{}", e.getMessage());
        }
    }

    private void sendPhoto(SendPhoto photo) {
        try {
            execute(photo);
        } catch (TelegramApiException e) {
            log.error("error from sending message, error msg:{}", e.getMessage());
        }
    }

    public void sendMessage(long chatId) {
        String textToSend = "Извините, такой команды нет.";
        SendMessage msg = new SendMessage(String.valueOf(chatId), textToSend);
        try {
            execute(msg);
        } catch (TelegramApiException e) {
            log.error("error from sending message, error msg:{}", e.getMessage());
        }
    }

    private void fillPozdravokWishes() {
        wishes = goodMorningRepository.findBySource("pozdravok.com");
    }

    private String getRandomWish() {
        var random = ThreadLocalRandom.current().nextInt(wishes.size());
        return wishes.get(random).getText();
    }

    private void doNothing(Long chatId) {
        sendMessage(chatId);
    }
}
