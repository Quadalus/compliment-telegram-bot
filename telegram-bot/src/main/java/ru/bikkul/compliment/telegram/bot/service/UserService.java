package ru.bikkul.compliment.telegram.bot.service;

import org.telegram.telegrambots.meta.api.objects.Message;
import ru.bikkul.compliment.telegram.bot.model.User;

public interface UserService {
    User saveUser(Message message);

    User updateUser(User user, long chatId);

    void deleteUser(long chatId);

    User getUser(long chatId);
}
