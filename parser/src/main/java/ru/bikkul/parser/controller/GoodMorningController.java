package ru.bikkul.parser.controller;


import lombok.RequiredArgsConstructor;
import org.springframework.http.HttpStatus;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.ResponseStatus;
import org.springframework.web.bind.annotation.RestController;
import ru.bikkul.parser.service.WishesParserService;

@RestController
@RequestMapping("/goodMorning")
@RequiredArgsConstructor
public class GoodMorningController {
    private final WishesParserService wishesParserServiceDatkiImpl;
    private final WishesParserService wishesParserServicePozdravokImpl;

    @PostMapping("/default/pozdravok")
    @ResponseStatus(HttpStatus.CREATED)
    public void saveWishesFromPozdravokSource() {
        wishesParserServicePozdravokImpl.saveWishesByDefaultSource();
    }

    @PostMapping("/default/datki")
    @ResponseStatus(HttpStatus.CREATED)
    public void saveWishesFromDatkiSource() {
        wishesParserServiceDatkiImpl.saveWishesByDefaultSource();
    }
}
