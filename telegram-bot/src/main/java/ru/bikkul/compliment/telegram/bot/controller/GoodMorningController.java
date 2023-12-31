package ru.bikkul.compliment.telegram.bot.controller;


import lombok.RequiredArgsConstructor;
import org.springframework.http.HttpStatus;
import org.springframework.web.bind.annotation.*;
import ru.bikkul.compliment.telegram.bot.dto.GoodMorningDto;
import ru.bikkul.compliment.telegram.bot.service.WishesParserService;

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

    @PostMapping("/user")
    @ResponseStatus(HttpStatus.CREATED)
    public GoodMorningDto saveWishesFromUserText(@RequestBody GoodMorningDto goodMorningDto) {
        return wishesParserServicePozdravokImpl.saveWishesByUser(goodMorningDto);
    }
}
