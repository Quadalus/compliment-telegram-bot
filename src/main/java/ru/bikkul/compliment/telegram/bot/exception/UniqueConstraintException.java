package ru.bikkul.compliment.telegram.bot.exception;

public class UniqueConstraintException extends RuntimeException{
    public UniqueConstraintException(String message) {
        super(message);
    }
}
