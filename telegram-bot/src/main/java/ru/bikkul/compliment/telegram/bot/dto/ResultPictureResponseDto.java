package ru.bikkul.compliment.telegram.bot.dto;

import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;

import java.util.List;

@NoArgsConstructor
@AllArgsConstructor
@Setter
@Getter
public class ResultPictureResponseDto {
    private String uuid;
    private String status;
    private List<String> images;
    private String censored;
    private Long chatId;
}
