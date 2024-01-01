package ru.bikkul.parser.service.impl;

import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.jsoup.Connection;
import org.jsoup.Jsoup;
import org.jsoup.nodes.Document;
import org.jsoup.nodes.Element;
import org.jsoup.select.Elements;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;
import ru.bikkul.parser.dto.GoodMorningDto;
import ru.bikkul.parser.mapper.GoodMorningDtoMapper;
import ru.bikkul.parser.model.GoodMorning;
import ru.bikkul.parser.repository.GoodMorningRepository;
import ru.bikkul.parser.service.WishesParserService;

import java.io.IOException;
import java.util.ArrayDeque;
import java.util.ArrayList;
import java.util.Deque;
import java.util.List;

@Slf4j
@Service
@RequiredArgsConstructor
public class WishesParserServiceDatkiImpl implements WishesParserService {
    private final String DEFAULT_SOURCE = "datki";
    private final GoodMorningRepository goodMorningRepository;
    private final List<GoodMorningDto> wishes = new ArrayList<>();

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

    private void parseWishes() {
        var urls = performUrl();
        int urlCount = urls.size();
        for (int i = 0; i < urlCount; i++) {
            getWishesFromUrl(urls.poll());
        }
    }

    private void getWishesFromUrl(String url) {
        Connection connection = Jsoup.connect(url);
        connection.userAgent("Mozilla/5.0 (Windows NT 10.0; Win64; x64) AppleWebKit/537.36 (KHTML, like Gecko) Chrome/118.0.0.0 YaBrowser/23.11.0.0 Safari/537.36");
        connection.timeout(5000);
        Document docCustomConn;

        try {
            docCustomConn = connection.get();

            Elements content = docCustomConn.getElementsByClass("entry-summary");
            for (Element element : content.select("p")) {
                wishes.add(new GoodMorningDto(element.text(), DEFAULT_SOURCE));
            }
        } catch (IOException e) {
            log.error("error from parsing good morning wishes, error msg:{}", e.getMessage());
        }
    }

    private Deque<String> performUrl() {
        var urls = new ArrayDeque<String>();
        String postfix = "page/";
        String morningUrl = "https://datki.net/s-dobrim-utrom/v-proze//";
        int pageSize = 2;

        for (int i = 0; i <= pageSize; i++) {
            String url;
            if (i == 0) {
                url = String.format("%s", morningUrl);
                urls.add(url);
                continue;
            }
            url = String.format("%s-%d%s", morningUrl, i, postfix);
            urls.add(url);
        }
        return urls;
    }
}
