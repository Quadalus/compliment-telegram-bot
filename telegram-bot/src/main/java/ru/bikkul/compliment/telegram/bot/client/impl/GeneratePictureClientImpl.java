package ru.bikkul.compliment.telegram.bot.client.impl;

import org.springframework.beans.factory.annotation.Value;
import org.springframework.stereotype.Component;
import org.springframework.web.reactive.function.client.ExchangeStrategies;
import org.springframework.web.reactive.function.client.WebClient;
import reactor.util.retry.Retry;
import ru.bikkul.compliment.telegram.bot.client.GeneratePictureClient;
import ru.bikkul.compliment.telegram.bot.dto.ResultPictureResponseDto;

import java.time.Duration;

@Component
public class GeneratePictureClientImpl implements GeneratePictureClient {
    private final WebClient webClient;
    private final String GENERATE_PATH;

    public GeneratePictureClientImpl(@Value("${bot.api.kandinsky.endpoint.base_url}") String base_url,
                                     @Value("${bot.api.kandinsky.client.buffer_size}") int bufferSize,
                                     @Value("${bot.api.kandinsky.endpoint.generate_path}") String generatePath) {

        this.GENERATE_PATH = generatePath;
        this.webClient = WebClient.builder()
                .baseUrl(base_url)
                .exchangeStrategies(ExchangeStrategies.builder()
                        .codecs(codec -> codec.defaultCodecs().maxInMemorySize(bufferSize)).build())
                .build();
    }

    @Override
    public ResultPictureResponseDto generatePicture(long chatId) {
        return webClient
                .get()
                .uri(GENERATE_PATH + chatId)
                .retrieve()
                .bodyToMono(ResultPictureResponseDto.class)
                .retryWhen(Retry.fixedDelay(3, Duration.ofSeconds(30)))
                .block();
    }
}
