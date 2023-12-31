package ru.bikkul.kadinsky.webclient.dto;

import lombok.Data;

@Data
public class GenerationPictureRequestDto {
    private final String type = "GENERATE";

    private final int width = 1024;

    private final int height = 1024;

    private final int num_images = 1;

    private final String style;

    private QueryParams generateParams;

    public GenerationPictureRequestDto(String style, String generateParams) {
        this.style = style;
        this.generateParams = new QueryParams(generateParams);
    }
}

record QueryParams(String query) {
}
