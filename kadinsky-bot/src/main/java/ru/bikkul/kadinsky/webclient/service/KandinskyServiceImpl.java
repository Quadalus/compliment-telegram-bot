package ru.bikkul.kadinsky.webclient.service;

import lombok.extern.slf4j.Slf4j;
import org.apache.commons.lang3.StringUtils;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.scheduling.annotation.Async;
import org.springframework.stereotype.Service;
import ru.bikkul.kadinsky.webclient.client.KadinskyClient;
import ru.bikkul.kadinsky.webclient.common.DefaultStyles;
import ru.bikkul.kadinsky.webclient.common.GenerateParam;
import ru.bikkul.kadinsky.webclient.common.Styles;
import ru.bikkul.kadinsky.webclient.dto.GenerationPictureRequestDto;
import ru.bikkul.kadinsky.webclient.dto.ResutPictureResponseDto;
import ru.bikkul.kadinsky.webclient.mapper.GenerationPictureMapperDto;

import java.time.Duration;
import java.util.Arrays;
import java.util.List;
import java.util.Map;
import java.util.concurrent.CompletableFuture;
import java.util.concurrent.ThreadLocalRandom;
import java.util.function.Function;
import java.util.stream.Collectors;

import static org.apache.commons.lang3.ThreadUtils.sleep;
import static ru.bikkul.kadinsky.webclient.common.GenerateParam.*;

@Slf4j
@Service
public class KandinskyServiceImpl implements KandinskyService {
    private final List<String> styles;
    private final List<String> defaultStyles;
    private final KadinskyClient kadinskyClient;
    private final Map<GenerateParam, List<String>> generationParameters;

    @Autowired
    public KandinskyServiceImpl(KadinskyClient kadinskyClient) {
        this.kadinskyClient = kadinskyClient;
        this.generationParameters = fillGenerationParameters();
        this.styles = fillStyles();
        this.defaultStyles = fillDefaultStyles();
        fillGenerationParameters();
    }

    @Override
    @Async
    public CompletableFuture<ResutPictureResponseDto> generatePicture(Long chatId) {
        GenerationPictureRequestDto generatePictureDto = generationData();
        var fullResponseDto = GenerationPictureMapperDto.toFullDto(kadinskyClient.generatePicture(generatePictureDto), chatId);
        var statusPicture = getStatusPicture(fullResponseDto.uuid());
        statusPicture.setChatId(chatId);
        log.info("picture to chat:{} has been generate", chatId);
        return CompletableFuture.completedFuture(statusPicture);
    }

    private GenerationPictureRequestDto generationData() {
        var style = getRandomStyle();
        var randomText = generateRandomText(style);
        log.info("random text is:{} | style:{}", randomText, style);
        return new GenerationPictureRequestDto(style, randomText);
    }

    private ResutPictureResponseDto getStatusPicture(String uuid) {
        var resutPictureDto = kadinskyClient.checkGenerateStatus(uuid);
        String status = resutPictureDto.getStatus();

        resutPictureDto = getResultPictureWithStatusDone(uuid, status, resutPictureDto);
        return resutPictureDto;
    }

    private ResutPictureResponseDto getResultPictureWithStatusDone(String uuid, String status, ResutPictureResponseDto resutPictureDto) {
        boolean condition = checkStatus(status);

        while (condition) {
            resutPictureDto = kadinskyClient.checkGenerateStatus(uuid);
            status = resutPictureDto.getStatus();
            condition = checkStatus(status);

            try {
                if (condition) {
                    sleep(Duration.ofSeconds(1));
                }
            } catch (InterruptedException e) {
                log.error("current thread interrupt");
            }
        }
        return resutPictureDto;
    }

    private static boolean checkStatus(String status) {
        return !StringUtils.containsOnly("DONE", status) && !StringUtils.containsOnly("FAIL", status);
    }

    private String getRandomStyle() {
        var randomRatio = 2.4;
        var random = ThreadLocalRandom.current().nextInt((int) ((styles.size() - 1) * randomRatio));

        return switch (random) {
            case 1 -> styles.get(1);
            case 2 -> styles.get(2);
            case 3 -> styles.get(3);
            default -> styles.get(0);
        };
    }

    private String generateRandomText(String style) {
        var sb = new StringBuilder("утро");
        var other = getOther();
        var location = getLocation(other);
        var weather = getWeather();
        var otherWater = getOtherWater(location);
        checkStrIsNotBlank(other, sb);
        checkStrIsNotBlank(location, sb);
        checkStrIsNotBlank(weather, sb);
        checkStrIsNotBlank(otherWater, sb);
        checkStyliIsDefault(style, sb);
        return StringUtils.strip(sb.toString());
    }

    private void checkStyliIsDefault(String style, StringBuilder sb) {
        if (StringUtils.containsOnly(style, "DEFAULT")) {
            sb.append(" в стиле ")
                    .append(getRandomDefaultStyle());
        }
    }

    private String getRandomDefaultStyle() {
        var random = ThreadLocalRandom.current().nextInt(defaultStyles.size());
        return defaultStyles.get(random);
    }

    private static void checkStrIsNotBlank(String other, StringBuilder sb) {
        if (!StringUtils.isBlank(other)) {
            sb.append(", ")
                    .append(other);
        }
    }

    private String getRandomWorld(GenerateParam type) {
        var params = generationParameters.get(type);
        var size = params.size();
        double randomRatio = type.equals(OTHER) ? 3 : 1.35;
        var random = ThreadLocalRandom.current().nextInt((int) (size * randomRatio));
        if (random >= size) {
            return "";
        }
        return params.get(random);
    }

    private String getOtherWater(String location) {
        if (StringUtils.containsAny(location, "море", "океан", "река")) {
            return getRandomWorld(OTHER_WATER);
        }
        return "";
    }

    private String getWeather() {
        return getRandomWorld(WEATHER);
    }

    private String getLocation(String other) {
        if (!StringUtils.isEmpty(other)) {
            return "";
        }
        return getRandomWorld(LOCATION);
    }

    private String getOther() {
        return getRandomWorld(OTHER);
    }

    private static List<String> fillDefaultStyles() {
        return Arrays.stream(DefaultStyles.values())
                .map(DefaultStyles::getDefaultStyle)
                .toList();
    }

    private static List<String> fillStyles() {
        return Arrays.stream(Styles.values())
                .map(Styles::getStyle)
                .toList();
    }

    private Map<GenerateParam, List<String>> fillGenerationParameters() {
        return Arrays.stream(values())
                .collect(Collectors.toMap(Function.identity(), GenerateParam::getParams));
    }
}
