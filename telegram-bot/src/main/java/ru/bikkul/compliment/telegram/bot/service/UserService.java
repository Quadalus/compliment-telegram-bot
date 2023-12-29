package ru.bikkul.compliment.telegram.bot.service;

import ru.bikkul.compliment.telegram.bot.model.User;

public interface UserService {
    User saveUser(User user);

    User updateUser(User user, long chatId);

    void deleteUser(long chatId);

    User getUser(long chatId);
}
