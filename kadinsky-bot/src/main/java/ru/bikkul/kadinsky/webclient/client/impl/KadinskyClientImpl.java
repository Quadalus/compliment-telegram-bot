package ru.bikkul.kadinsky.webclient.client.impl;

import org.springframework.beans.factory.annotation.Value;
import org.springframework.http.MediaType;
import org.springframework.http.client.MultipartBodyBuilder;
import org.springframework.stereotype.Component;
import org.springframework.web.reactive.function.BodyInserters;
import org.springframework.web.reactive.function.client.ExchangeStrategies;
import org.springframework.web.reactive.function.client.WebClient;
import ru.bikkul.kadinsky.webclient.client.KadinskyClient;
import ru.bikkul.kadinsky.webclient.dto.GenerationPictureRequestDto;
import ru.bikkul.kadinsky.webclient.dto.GenerationPictureResponseDto;
import ru.bikkul.kadinsky.webclient.dto.ResutPictureResponseDto;

@Component
public class KadinskyClientImpl implements KadinskyClient {
    public static final int BUFFER_SIZE = 10485760;
    private final WebClient webClient;
    private final String GENERATE_PATH;
    private final String STATUS_PATH;
    private final String SERVICE_AVAILABLE_PATH;

    public KadinskyClientImpl(@Value("${kandinsky.api.key}") String apiKey,
                              @Value("${kandinsky.api.secret}") String apiSecret,
                              @Value("${kandinsky.api.endpoint.url}") String url,
                              @Value("${kandinsky.api.endpoint.generate_path}") String generatePath,
                              @Value("${kandinsky.api.endpoint.status_path}") String statusPath,
                              @Value("${kandinsky.api.endpoint.service_available_path}") String serviceAvailablePath) {
        this.GENERATE_PATH = generatePath;
        this.STATUS_PATH = statusPath;
        this.SERVICE_AVAILABLE_PATH = serviceAvailablePath;

        String key = "Key %s".formatted(apiKey);
        String secret = "Secret %s".formatted(apiSecret);
        this.webClient = WebClient.builder()
                .baseUrl(url)
                .defaultHeader("X-Key", key)
                .defaultHeader("X-Secret", secret)
                .exchangeStrategies(ExchangeStrategies.builder()
                        .codecs(codec -> codec.defaultCodecs().maxInMemorySize(BUFFER_SIZE)).build())
                .build();
    }

    @Override
    public GenerationPictureResponseDto generatePicture(GenerationPictureRequestDto queryPicture) {
        MultipartBodyBuilder builder = new MultipartBodyBuilder();
        builder.part("params", queryPicture);
        builder.part("model_id", 4);

        return webClient
                .post()
                .uri(uriBuilder -> uriBuilder
                        .path(GENERATE_PATH)
                        .build())
                .contentType(MediaType.MULTIPART_FORM_DATA)
                .body(BodyInserters.fromMultipartData(builder.build()))
                .retrieve()
                .bodyToMono(GenerationPictureResponseDto.class)
                .block();
    }

    @Override
    public String checkServiceAvailable() {
        return null;
    }

    @Override
    public ResutPictureResponseDto checkGenerateStatus(String uuid) {
        return webClient
                .get()
                .uri(uriBuilder -> uriBuilder
                        .path(STATUS_PATH)
                        .path(uuid)
                        .build())
                .retrieve()
                .bodyToMono(ResutPictureResponseDto.class)
                .block();
    }
}
