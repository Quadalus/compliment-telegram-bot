package ru.bikkul.compliment.telegram.bot.service;

import ru.bikkul.compliment.telegram.bot.model.UserSetting;

public interface UserSettingService {
    UserSetting saveUserSetting(long chatId);

    UserSetting updateUserSetting(UserSetting userSetting, long chatId);

    void deleteUserSetting(long chatId);

    UserSetting getUserSettings(long chatId);

    void setDefaultSetting(long chatId);
}
