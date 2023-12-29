package ru.bikkul.compliment.telegram.bot.service;

import ru.bikkul.compliment.telegram.bot.model.UserSetting;

public interface UserSettingService {
    UserSetting saveUserSetting(UserSetting userSetting);

    UserSetting updateUserSetting(UserSetting userSetting, long chatId);

    void deleteUserSetting(long chatId);

    UserSetting getUserSettings(long chatId);
}
