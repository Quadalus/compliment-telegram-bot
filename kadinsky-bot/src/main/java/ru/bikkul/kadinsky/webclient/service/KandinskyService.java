package ru.bikkul.kadinsky.webclient.service;

import ru.bikkul.kadinsky.webclient.dto.ResutPictureResponseDto;

public interface KandinskyService {
    ResutPictureResponseDto generatePicture(Long charId);
}
