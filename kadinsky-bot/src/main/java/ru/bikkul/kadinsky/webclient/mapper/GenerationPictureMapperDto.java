package ru.bikkul.kadinsky.webclient.mapper;

import ru.bikkul.kadinsky.webclient.dto.GenerationPictureResponseDto;
import ru.bikkul.kadinsky.webclient.dto.GenerationPictureResponseFullDto;

public class GenerationPictureMapperDto {
    private GenerationPictureMapperDto() {
    }

    public static GenerationPictureResponseFullDto toFullDto(GenerationPictureResponseDto responseDto, Long charId) {
        return new GenerationPictureResponseFullDto(responseDto.status(), responseDto.uuid(), charId);
    }
}
