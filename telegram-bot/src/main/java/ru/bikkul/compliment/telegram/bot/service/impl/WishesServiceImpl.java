package ru.bikkul.compliment.telegram.bot.service.impl;

import lombok.extern.slf4j.Slf4j;
import org.springframework.stereotype.Service;
import ru.bikkul.compliment.telegram.bot.model.GoodMorning;
import ru.bikkul.compliment.telegram.bot.repository.GoodMorningRepository;
import ru.bikkul.compliment.telegram.bot.util.handler.MessageHandler;

import java.util.List;
import java.util.concurrent.ThreadLocalRandom;

@Slf4j
@Service
public class WishesServiceImpl {
    private final GoodMorningRepository goodMorningRepository;
    private final MessageHandler messageHandler;
    private List<GoodMorning> wishes;

    public WishesServiceImpl(GoodMorningRepository goodMorningRepository, MessageHandler messageHandler) {
        this.goodMorningRepository = goodMorningRepository;
        this.messageHandler = messageHandler;
    }

    private void fillPozdravokWishes() {
        wishes = goodMorningRepository.findBySource("pozdravok.com");
    }

    public String getRandomWish() {
        var random = ThreadLocalRandom.current().nextInt(wishes.size());
        return wishes.get(random).getText();
    }

    public void sendRandomWish(long chatId) {
        var wish = getRandomWish();
        messageHandler.sendMessage(chatId, wish);
    }
}
