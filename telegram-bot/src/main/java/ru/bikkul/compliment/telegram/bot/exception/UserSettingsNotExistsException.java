package ru.bikkul.compliment.telegram.bot.exception;

public class UserSettingsNotExistsException extends RuntimeException {
    public UserSettingsNotExistsException(String message) {
        super(message);
    }
}
