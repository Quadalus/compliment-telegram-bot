package ru.bikkul.compliment.telegram.bot.config;

import lombok.Getter;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.context.annotation.Configuration;
import org.springframework.context.annotation.PropertySource;

@Configuration
@Getter
@PropertySource("classpath:application.yaml")
public class BotConfig {
    @Value("${bot.name}")
    private final String botName;

    @Value("${bot.token}")
    private final String botToken;

    public BotConfig(@Value("${bot.name}") String botName,
                     @Value("${bot.token}") String botToken) {
        this.botName = botName;
        this.botToken = botToken;
    }
}
