package ru.bikkul.compliment.telegram.bot.util.common;

import org.telegram.telegrambots.meta.api.objects.commands.BotCommand;

import java.util.List;

public class BotConst {
    public static final String DEFAULT_CRON_EXPRESSION = "0 0 8 ? * * *";

    public static final String START_MESSAGE = """
            Привет, Я Good Morning Bot 🤖! Я умею:
                           
             • генерировать картинку каждое утро 🖼
             • присылать пожелания с добрым утром
             • присылать интересные факты
                             
            Бот по умолчанию присылает пожелания с добрым утром в 08:00. Для смены времени  воспользуетесь командой /time
                             
            Для подробной информации  воспользуетесь командой /help
             """;

    public static final String HELP_MESSAGE = """
            Доступные команды:
            /start - начать работу с ботом.
            /random - получить случайную картинку или пожелание.
            /help - помощь по боту.
            /info - посмотреть текущие настройки.
            /time - установить новое время для доставки пожеланий.
            /stop - остановить приложение и сбросить настройки.
            """;

    public static final List<BotCommand> LIST_OF_COMMAND = List.of(
            new BotCommand("/start", "Запуск приложения."),
            new BotCommand("/random", "Получить случайное пожелание на утро."),
            new BotCommand("/time", "Установить время."),
            new BotCommand("/info", "Получить текущие настройки."),
            new BotCommand("/help", "Помощь."),
            new BotCommand("/stop", "Остановка приложения.")
    );

    public static final String RANDOM_ROW_TEXT = "Получить случайную картинку или пожелание";

    public static final String TIME_ROW_TEXT = "Установить новое время уведомленний";

    public static final String START_ROW_TEXT = "Запустить приложение";
}
