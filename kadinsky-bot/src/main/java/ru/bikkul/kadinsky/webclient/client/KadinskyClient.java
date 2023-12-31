package ru.bikkul.kadinsky.webclient.client;

import ru.bikkul.kadinsky.webclient.dto.GenerationPictureRequestDto;
import ru.bikkul.kadinsky.webclient.dto.GenerationPictureResponseDto;
import ru.bikkul.kadinsky.webclient.dto.ResutPictureResponseDto;

public interface KadinskyClient {
    GenerationPictureResponseDto generatePicture(GenerationPictureRequestDto queryPicture);
    String checkServiceAvailable();
    ResutPictureResponseDto checkGenerateStatus(String uuid);
}
