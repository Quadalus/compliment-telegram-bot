package ru.bikkul.kadinsky.webclient.service;

import ru.bikkul.kadinsky.webclient.dto.ResutPictureResponseDto;

import java.util.concurrent.CompletableFuture;

public interface KandinskyService {
    CompletableFuture<ResutPictureResponseDto> generatePicture(Long charId);
}
