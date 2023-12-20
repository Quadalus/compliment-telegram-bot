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
    private final WishesParserService goodMorningParser;

    @PostMapping("/default")
    @ResponseStatus(HttpStatus.CREATED)
    public void saveWishesFromDefaultSource() {
        goodMorningParser.saveWishesByDefaultSource();
    }

    @PostMapping("/user")
    @ResponseStatus(HttpStatus.CREATED)
    public GoodMorningDto saveWishesFromUserText(@RequestBody GoodMorningDto goodMorningDto) {
        return goodMorningParser.saveWishesByUser(goodMorningDto);
    }
}
