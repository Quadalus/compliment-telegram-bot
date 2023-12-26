package ru.bikkul.kadinsky.webclient.controller;

import lombok.RequiredArgsConstructor;
import org.springframework.web.bind.annotation.*;
import ru.bikkul.kadinsky.webclient.dto.ResutPictureResponseDto;
import ru.bikkul.kadinsky.webclient.service.KandinskyService;

@RestController
@RequiredArgsConstructor
public class KadinskyController {
    private final KandinskyService kandinskyService;

    @GetMapping("/generate/{charId}")
    public ResutPictureResponseDto generatePicture(@PathVariable Long charId) {
        return kandinskyService.generatePicture(charId);
    }
}
