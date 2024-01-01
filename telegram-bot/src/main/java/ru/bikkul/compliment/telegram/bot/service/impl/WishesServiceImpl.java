package ru.bikkul.compliment.telegram.bot.service.impl;

import lombok.extern.slf4j.Slf4j;
import org.springframework.stereotype.Service;
import ru.bikkul.compliment.telegram.bot.repository.GoodMorningRepository;
import ru.bikkul.compliment.telegram.bot.service.WishesService;
import ru.bikkul.compliment.telegram.bot.util.handler.MessageSender;

import java.util.List;
import java.util.concurrent.ThreadLocalRandom;

@Slf4j
@Service
public class WishesServiceImpl implements WishesService {
    private final GoodMorningRepository goodMorningRepository;
    private final MessageSender messageSender;
    private List<Integer> wishesId;

    public WishesServiceImpl(GoodMorningRepository goodMorningRepository, MessageSender messageSender) {
        this.goodMorningRepository = goodMorningRepository;
        this.messageSender = messageSender;
        fillWishesId();
    }

    @Override
    public String getRandomWish() {
        var random = ThreadLocalRandom.current().nextInt(wishesId.size());
        return goodMorningRepository.getTextById(wishesId.get(random));
    }

    @Override
    public void sendRandomWish(long chatId) {
        var wish = getRandomWish();
        messageSender.sendMessage(chatId, wish);
    }

    private void fillWishesId() {
        wishesId = goodMorningRepository.findAllIdBySource("site");
    }
}
