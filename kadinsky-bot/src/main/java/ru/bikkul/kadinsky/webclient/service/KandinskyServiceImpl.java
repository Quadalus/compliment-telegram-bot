package ru.bikkul.kadinsky.webclient.service;

import lombok.extern.slf4j.Slf4j;
import org.apache.commons.io.FileUtils;
import org.apache.commons.lang3.StringUtils;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import ru.bikkul.kadinsky.webclient.client.KadinskyClient;
import ru.bikkul.kadinsky.webclient.common.DefaultStyles;
import ru.bikkul.kadinsky.webclient.common.GenerateParam;
import ru.bikkul.kadinsky.webclient.common.Styles;
import ru.bikkul.kadinsky.webclient.dto.GenerationPictureRequestDto;
import ru.bikkul.kadinsky.webclient.dto.ResutPictureResponseDto;
import ru.bikkul.kadinsky.webclient.mapper.GenerationPictureMapperDto;

import java.io.File;
import java.io.IOException;
import java.time.Duration;
import java.util.Arrays;
import java.util.Base64;
import java.util.List;
import java.util.Map;
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
    public ResutPictureResponseDto generatePicture(Long chatId) {
        String style = getRandomStyle();
        String randomText = generateRandomText(style);
        log.info("random text is:{} | style:{}", randomText, style);
        GenerationPictureRequestDto generatePictureDto = new GenerationPictureRequestDto(style, randomText);
        var fullResponseDto = GenerationPictureMapperDto.toFullDto(kadinskyClient.generatePicture(generatePictureDto), chatId);
        var statusPicture = getStatusPicture(fullResponseDto.uuid());
        statusPicture.setChatId(chatId);
        return statusPicture;
    }

    private ResutPictureResponseDto getStatusPicture(String uuid) {
        var resutPictureDto = kadinskyClient.checkGenerateStatus(uuid);
        String status = resutPictureDto.getStatus();

        resutPictureDto = getResultPictureWithStatusDone(uuid, status, resutPictureDto);
        var images = resutPictureDto.getImages();
        if (!(images == null) && !images.isEmpty()) {
            var s = images.get(0);
            byte[] decodedBytes = Base64.getDecoder().decode(s);
            try {
                FileUtils.writeByteArrayToFile(new File("src/main/resources/img/picture%d.jpg"
                        .formatted(ThreadLocalRandom.current().nextInt(Integer.MAX_VALUE))), decodedBytes);
            } catch (IOException e) {
                log.error("error");
            }
        }
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
        double randomRatio = 2.4;
        int random = ThreadLocalRandom.current().nextInt((int) ((styles.size() - 1) * randomRatio));
        return switch (random) {
            case 1 -> styles.get(1);
            case 2 -> styles.get(2);
            case 3 -> styles.get(3);
            default -> styles.get(0);
        };
    }

    private String generateRandomText(String style) {
        StringBuilder sb = new StringBuilder("утро");
        String other = getOther();
        String location = getLocation(other);
        String weather = getWeather();
        String otherWater = getOtherWater(location);
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
        int random = ThreadLocalRandom.current().nextInt(defaultStyles.size());
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
