package ru.bikkul.compliment.telegram.bot.util.mapper;

import org.telegram.telegrambots.meta.api.objects.Message;
import ru.bikkul.compliment.telegram.bot.model.User;

public class UserMapper {
    private UserMapper() {
    }

    public static User userFromMessage(Message message, long chatId) {
        var userName = message.getChat().getUserName() == null ? "undefined" : message.getChat().getUserName();
        var firstName = message.getChat().getFirstName() == null ? "undefined" : message.getChat().getFirstName();
        var lastName = message.getChat().getLastName() == null ? "undefined" : message.getChat().getLastName();

        return new User(chatId, userName, firstName, lastName);
    }
}
