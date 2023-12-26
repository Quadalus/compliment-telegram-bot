package ru.bikkul.compliment.telegram.bot.service.impl;

import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.jsoup.Connection;
import org.jsoup.Jsoup;
import org.jsoup.nodes.Document;
import org.jsoup.nodes.Element;
import org.jsoup.select.Elements;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;
import ru.bikkul.compliment.telegram.bot.dto.GoodMorningDto;
import ru.bikkul.compliment.telegram.bot.exception.UniqueConstraintException;
import ru.bikkul.compliment.telegram.bot.model.GoodMorning;
import ru.bikkul.compliment.telegram.bot.repository.GoodMorningRepository;
import ru.bikkul.compliment.telegram.bot.service.WishesParserService;
import ru.bikkul.compliment.telegram.bot.util.mapper.GoodMorningDtoMapper;

import java.io.IOException;
import java.util.ArrayDeque;
import java.util.ArrayList;
import java.util.Deque;
import java.util.List;

@Slf4j
@Service
@RequiredArgsConstructor
public class GoodMorningParserImpl implements WishesParserService {
    private final String DEFAULT_SOURCE = "pozdravok.com";
    private final GoodMorningRepository goodMorningRepository;
    private final List<GoodMorningDto> wishes = new ArrayList<>();


    @Override
    @Transactional
    public void saveWishesByDefaultSource() {
        if (goodMorningRepository.existsBySource(DEFAULT_SOURCE)) {
            return;
        }
        parseWishes();
        List<GoodMorning> goodMornings = goodMorningRepository.saveAll(wishes
                .stream()
                .map(GoodMorningDtoMapper::fromDto)
                .toList());
        log.info("good morning wishes has been save, wishes size:{}", goodMornings.size());
    }

    @Override
    @Transactional
    public GoodMorningDto saveWishesByUser(GoodMorningDto goodMorningDto) {
        if (goodMorningRepository.existsByText(goodMorningDto.text())) {
            throw new UniqueConstraintException("Текст с таким пожеланием, уже существует!");
        }
        GoodMorning savedGoodMorning = goodMorningRepository.save(GoodMorningDtoMapper.fromDto(goodMorningDto));
        return GoodMorningDtoMapper.toDto(savedGoodMorning);
    }

    private void parseWishes() {
        var urls = performUrl();
        int urlCount = urls.size();
        for (int i = 0; i < urlCount; i++) {
            getWishesFromUrl(urls.poll());
        }
    }

    private void getWishesFromUrl(String url) {
        Connection connection = Jsoup.connect(url);
        connection.userAgent("Mozilla");
        connection.timeout(5000);
        Document docCustomConn;

        try {
            docCustomConn = connection.get();

            Elements content = docCustomConn.getElementsByClass("sfst");
            for (Element element : content) {
                wishes.add(new GoodMorningDto(element.text(), DEFAULT_SOURCE));
            }
        } catch (IOException e) {
            log.error("error from parsing good morning wishes, error msg:{}", e.getMessage());
        }
    }

    private Deque<String> performUrl() {
        var urls = new ArrayDeque<String>();
        String postfix = ".htm";
        String morningUrl = "https://pozdravok.com/pozdravleniya/lyubov/dobroe-utro/proza";
        int pageSize = 43;

        for (int i = 0; i <= pageSize; i++) {
            String url;
            if (i == 0) {
                url = String.format("%s%s", morningUrl, postfix);
                urls.add(url);
                continue;
            }
            url = String.format("%s-%d%s", morningUrl, i, postfix);
            urls.add(url);
        }
        return urls;
    }
}
