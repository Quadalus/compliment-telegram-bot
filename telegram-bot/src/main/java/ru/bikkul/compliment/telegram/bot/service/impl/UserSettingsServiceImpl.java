package ru.bikkul.compliment.telegram.bot.service.impl;

import lombok.extern.slf4j.Slf4j;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;
import ru.bikkul.compliment.telegram.bot.exception.UserNotExistsException;
import ru.bikkul.compliment.telegram.bot.model.UserSetting;
import ru.bikkul.compliment.telegram.bot.repository.UserSettingsRepository;
import ru.bikkul.compliment.telegram.bot.service.UserSettingService;

@Slf4j
@Service
@Transactional(readOnly = true)
public class UserSettingsServiceImpl implements UserSettingService {
    private final UserSettingsRepository userSettingsRepository;

    public UserSettingsServiceImpl(UserSettingsRepository userSettingsRepository) {
        this.userSettingsRepository = userSettingsRepository;
    }


    @Override
    @Transactional
    public UserSetting saveUserSetting(UserSetting userSetting) {
        var savedUser = userSettingsRepository.save(userSetting);
        log.info("Пользовательские настройки с id:%s, были сохранены".formatted(savedUser.getChatId()));
        return savedUser;
    }

    @Override
    @Transactional
    public UserSetting updateUserSetting(UserSetting userSetting, long chatId) {
        checkUserExists(chatId);
        var updatedUserSetting = userSettingsRepository.save(userSetting);
        log.info("Пользовательские настройки с id:%s, были обновлены".formatted(updatedUserSetting.getChatId()));
        return updatedUserSetting;
    }

    @Override
    @Transactional
    public void deleteUserSetting(long chatId) {
        checkUserExists(chatId);
        userSettingsRepository.deleteById(chatId);
        log.info("Пользовательские настройки с id:%s, были удалены".formatted(chatId));
    }

    @Override
    public UserSetting getUserSettings(long chatId) {
        return userSettingsRepository.findById(chatId)
                .orElseThrow(() -> new UserNotExistsException("Пользовательские настройки с id:%s, на найден".formatted(chatId)));
    }

    private void checkUserExists(long chatId) {
        if (!userSettingsRepository.existsById(chatId)) {
            throw new UserNotExistsException("Пользовательские настройки с id:%s, не найден".formatted(chatId));
        }
    }
}
